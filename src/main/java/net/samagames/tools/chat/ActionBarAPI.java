package net.samagames.tools.chat;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * An utility class to send action bars to the players.
 */
public final class ActionBarAPI
{
	private static boolean enabled = true;

	private static String nmsVersion;

	private static Class<?> craftPlayerClass;
	private static Class<?> packetPlayOutChatClass;
	private static Class<?> packetClass;
	private static Class<?> chatSerializerClass;
	private static Class<?> iChatBaseComponentClass;
	private static Class<?> chatComponentTextClass;

	private static Map<UUID, String> actionMessages = new ConcurrentHashMap<>();

	private static Runnable actionMessagesUpdater = null;
	private static boolean actionMessagesUpdaterRunning = false;
	private static BukkitTask actionMessagesUpdaterTask = null;

	static
	{
		nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
		nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);

		try
		{
			iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
			packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
			craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
			packetClass = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");

			if (nmsVersion.equalsIgnoreCase("v1_8_R1") || !nmsVersion.startsWith("v1_8_"))
			{
				chatSerializerClass = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
			}
			else
			{
				chatComponentTextClass = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
			}
		}
		catch (Exception e)
		{
			enabled = false;
		}

		initActionMessageUpdater();
	}

	private ActionBarAPI() {}

	/**
	 * Sends a constant message to the given player.
	 * <p/>
	 * This message will remain on the screen until the {@link #removeMessage} method is called,
	 * or the server is stopped.
	 *
	 * @param player  The player.
	 * @param message The message to display.
	 */
	public static void sendPermanentMessage(Player player, String message)
	{
		actionMessages.put(player.getUniqueId(), message);
		sendMessage(player, message);

		checkActionMessageUpdaterRunningState();
	}

	/**
	 * Sends a constant message to the given player.
	 * <p/>
	 * This message will remain on the screen until the {@link #removeMessage} method is called,
	 * or the server is stopped.
	 *
	 * @param playerUUID The player's UUID.
	 * @param message    The message to display.
	 */
	public static void sendPermanentMessage(UUID playerUUID, String message)
	{
		actionMessages.put(playerUUID, message);
		sendMessage(playerUUID, message);

		checkActionMessageUpdaterRunningState();
	}

	/**
	 * Sends an action-bar message to the given player.
	 * <p/>
	 * This message will remain approximately three seconds.
	 *
	 * @param playerUUID The player's UUID.
	 * @param message    The message.
	 */
	public static void sendMessage(UUID playerUUID, String message)
	{
		sendMessage(Bukkit.getPlayer(playerUUID), message);
	}

	/**
	 * Sends an action-bar message to the given player.
	 * <p/>
	 * This message will remain approximately three seconds.
	 *
	 * @param player  The player.
	 * @param message The message.
	 *
	 * @author ConnorLinfoot (https://github.com/ConnorLinfoot/ActionBarAPI/).
	 */
	public static void sendMessage(Player player, String message)
	{
		if (!enabled || player == null || message == null) return;

		try
		{
			Object craftPlayer = craftPlayerClass.cast(player);
			Object chatPacket;

			if (nmsVersion.equalsIgnoreCase("v1_8_R1") || !nmsVersion.startsWith("v1_8_"))
			{
				Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
				Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
				chatPacket = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
			}
			else
			{
				Object o = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
				chatPacket = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(o, (byte) 2);
			}

			Object handle = craftPlayerClass.getDeclaredMethod("getHandle").invoke(craftPlayer);
			Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);

			playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass).invoke(playerConnection, chatPacket);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removes the action bar message displayed to the given player.
	 *
	 * @param player  The player.
	 * @param instant If {@code true}, the message will be removed instantly. Else, it will dismiss progressively.
	 *                Please note that in that case, the message may be displayed a few more seconds.
	 */
	public static void removeMessage(Player player, boolean instant)
	{
		actionMessages.remove(player.getUniqueId());

		if (instant)
		{
			sendMessage(player, "");
		}

		checkActionMessageUpdaterRunningState();
	}

	/**
	 * Removes the action bar message displayed to the given player.
	 *
	 * @param playerUUID The UUID of the player.
	 * @param instant    If {@code true}, the message will be removed instantly. Else, it will dismiss progressively.
	 *                   Please note that in that case, the message may be displayed a few more seconds.
	 */
	public static void removeMessage(UUID playerUUID, boolean instant)
	{
		actionMessages.remove(playerUUID);

		if (instant)
		{
			sendMessage(playerUUID, "");
		}

		checkActionMessageUpdaterRunningState();
	}

	/**
	 * Removes the action bar message displayed to the given player.
	 *
	 * @param player The player.
	 */
	public static void removeMessage(Player player)
	{
		removeMessage(player, false);
	}

	/**
	 * Removes the action bar message displayed to the given player.
	 *
	 * @param playerUUID The UUID of the player.
	 */
	public static void removeMessage(UUID playerUUID)
	{
		removeMessage(playerUUID, false);
	}

	/**
	 * Initializes the {@link Runnable} that will re-send the permanent action messages
	 * to the players.
	 */
	private static void initActionMessageUpdater()
	{
		actionMessagesUpdater = () -> {
			for (Map.Entry<UUID, String> entry : actionMessages.entrySet())
			{
				Player player = Bukkit.getPlayer(entry.getKey());
				if (player != null && player.isOnline())
				{
					sendMessage(player, entry.getValue());
				}
			}
		};
	}

	/**
	 * Checks if the task sending the permanent actions message needs to run and is not
	 * running, or is useless and running. Stops or launches the task if needed.
	 */
	private static void checkActionMessageUpdaterRunningState()
	{
		int messagesCount = actionMessages.size();

		if(messagesCount == 0 && actionMessagesUpdaterRunning)
		{
			actionMessagesUpdaterTask.cancel();
			actionMessagesUpdaterTask = null;
			actionMessagesUpdaterRunning = false;
		}
		else if(messagesCount > 0 && !actionMessagesUpdaterRunning)
		{
			actionMessagesUpdaterTask = Bukkit.getScheduler().runTaskTimer(SamaGamesAPI.get().getPlugin(), actionMessagesUpdater, 2l, 30l);
			actionMessagesUpdaterRunning = true;
		}
	}
}
