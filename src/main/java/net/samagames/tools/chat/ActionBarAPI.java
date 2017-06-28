package net.samagames.tools.chat;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Reflection;
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

	private static Map<UUID, String> actionMessages = new ConcurrentHashMap<>();

	private static Runnable actionMessagesUpdater = null;
	private static boolean actionMessagesUpdaterRunning = false;
	private static BukkitTask actionMessagesUpdaterTask = null;

	static
	{
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
	 */
	public static void sendMessage(Player player, String message)
	{
		if (!enabled || player == null || message == null)
			return;

		Reflection.sendPacket(player, new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), ChatMessageType.GAME_INFO));
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
