package net.samagames.tools.BarAPI;

import net.samagames.tools.BarAPI.nms.FakeWither;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Allows plugins to safely set a health bar message.
 *
 * @author James Mortemore
 */

public class BarAPI implements Listener {
	private static HashMap<UUID, FakeWither> wither_players = new HashMap<>();
	private static HashMap<UUID, Integer> timers = new HashMap<>();

	private static BarAPI api;

	private static JavaPlugin plugin;

	public BarAPI(JavaPlugin plugin)
	{
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		// Continuously fix for 1.8 clients
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().stream().forEach(p -> handleTeleport(p, p.getLocation()));
			}
		}.runTaskTimerAsynchronously(plugin, 0, 5);
	}

	/**
	 * Set a message for all players.<br>
	 * It will remain there until the player logs off or another plugin overrides it.<br>
	 * This method will show a full health bar and will cancel any running timers.
	 *
	 * @param message
	 *            The message shown.<br>
	 *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
	 *            It will be cut to that size automatically.
	 * @see BarAPI#setMessage(player, message)
	 */
	public static void setMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			setMessage(player, message);
		}
	}

	/**
	 * Set a message for the given player.<br>
	 * It will remain there until the player logs off or another plugin overrides it.<br>
	 * This method will show a full health bar and will cancel any running timers.
	 *
	 * @param player
	 *            The player who should see the given message.
	 * @param message
	 *            The message shown to the player.<br>
	 *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
	 *            It will be cut to that size automatically.
	 */
	public static void setMessage(Player player, String message) {

		FakeWither wither = getWither(player, message);

		wither.name = cleanMessage(message);
		wither.health = FakeWither.MAX_HEALTH;

		cancelTimer(player);

		sendWither(wither, player);

	}

	/**
	 * Set a message for all players.<br>
	 * It will remain there for each player until the player logs off or another plugin overrides it.<br>
	 * This method will show a health bar using the given percentage value and will cancel any running timers.
	 *
	 * @param message
	 *            The message shown to the player.<br>
	 *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
	 *            It will be cut to that size automatically.
	 * @param percent
	 *            The percentage of the health bar filled.<br>
	 *            This value must be between 0F (inclusive) and 100F (inclusive).
	 * @throws IllegalArgumentException
	 *             If the percentage is not within valid bounds.
	 * @see BarAPI#setMessage(player, message, percent)
	 */
	public static void setMessage(String message, float percent) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			setMessage(player, message, percent);
		}
	}

	/**
	 * Set a message for the given player.<br>
	 * It will remain there until the player logs off or another plugin overrides it.<br>
	 * This method will show a health bar using the given percentage value and will cancel any running timers.
	 *
	 * @param player
	 *            The player who should see the given message.
	 * @param message
	 *            The message shown to the player.<br>
	 *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
	 *            It will be cut to that size automatically.
	 * @param percent
	 *            The percentage of the health bar filled.<br>
	 *            This value must be between 0F (inclusive) and 100F (inclusive).
	 * @throws IllegalArgumentException
	 *             If the percentage is not within valid bounds.
	 */
	public static void setMessage(Player player, String message, float percent) {
		Validate.isTrue(0F <= percent && percent <= 100F, "Percent must be between 0F and 100F, but was: ", percent);

		FakeWither wither = getWither(player, message);

		wither.name = cleanMessage(message);
		wither.health = (percent / 100f) * FakeWither.MAX_HEALTH;

		cancelTimer(player);

		sendWither(wither, player);

	}

	/**
	 * Set a message for all players.<br>
	 * It will remain there for each player until the player logs off or another plugin overrides it.<br>
	 * This method will use the health bar as a decreasing timer, all previously started timers will be cancelled.<br>
	 * The timer starts with a full bar.<br>
	 * The health bar will be removed automatically if it hits zero.
	 *
	 * @param message
	 *            The message shown.<br>
	 *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
	 *            It will be cut to that size automatically.
	 * @param seconds
	 *            The amount of seconds displayed by the timer.<br>
	 *            Supports values above 1 (inclusive).
	 * @throws IllegalArgumentException
	 *             If seconds is zero or below.
	 * @see BarAPI#setMessage(player, message, seconds)
	 */
	public static void setMessage(String message, int seconds) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			setMessage(player, message, seconds);
		}
	}

	/**
	 * Set a message for the given player.<br>
	 * It will remain there until the player logs off or another plugin overrides it.<br>
	 * This method will use the health bar as a decreasing timer, all previously started timers will be cancelled.<br>
	 * The timer starts with a full bar.<br>
	 * The health bar will be removed automatically if it hits zero.
	 *
	 * @param player
	 *            The player who should see the given timer/message.
	 * @param message
	 *            The message shown to the player.<br>
	 *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
	 *            It will be cut to that size automatically.
	 * @param seconds
	 *            The amount of seconds displayed by the timer.<br>
	 *            Supports values above 1 (inclusive).
	 * @throws IllegalArgumentException
	 *             If seconds is zero or below.
	 */
	public static void setMessage(final Player player, String message, int seconds) {
		Validate.isTrue(seconds > 0, "Seconds must be above 1 but was: ", seconds);

		FakeWither wither = getWither(player, message);

		wither.name = cleanMessage(message);
		wither.health = FakeWither.MAX_HEALTH;

		//final float witherHealthMinus = FakeWither.MAX_HEALTH / seconds;

		cancelTimer(player);

		timers.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			FakeWither with = getWither(player, "");
			//with.health -= witherHealthMinus;

			if (with.health <= 1) {
				removeBar(player);
				cancelTimer(player);
			} else {
				sendWither(with, player);
			}
		}, 20L, 20L).getTaskId());

		sendWither(wither, player);
	}

	/**
	 * Checks whether the given player has a bar.
	 *
	 * @param player
	 *            The player who should be checked.
	 * @return True, if the player has a bar, False otherwise.
	 */
	@Deprecated
	public static boolean hasBar(Player player){
		return hasWitherBar(player);
	}

	public static boolean hasWitherBar(Player player) {
		return wither_players.get(player.getUniqueId()) != null;
	}

	/**
	 * Removes the bar from the given player.<br>
	 * If the player has no bar, this method does nothing.
	 *
	 * @param player
	 *            The player whose bar should be removed.
	 */
	public static void removeBar(Player player) {
		if (!hasWitherBar(player))
			return;

		if(hasWitherBar(player)){
			Util.sendPacket(player, getWither(player, "").getDestroyPacket());
			wither_players.remove(player.getUniqueId());
			cancelTimer(player);
		}
	}

	/**
	 * Modifies the health of an existing bar.<br>
	 * If the player has no bar, this method does nothing.
	 *
	 * @param player
	 *            The player whose bar should be modified.
	 * @param percent
	 *            The percentage of the health bar filled.<br>
	 *            This value must be between 0F and 100F (inclusive).
	 */
	public static void setHealth(Player player, float percent) {
		if (!hasWitherBar(player))
			return;

		if(hasWitherBar(player)){
			FakeWither wither = getWither(player, "");
			wither.health = (percent / 100f) * FakeWither.MAX_HEALTH;

			cancelTimer(player);

			if (percent == 0) {
				removeBar(player);
			} else {
				sendWither(wither, player);
			}
		}
	}

	/**
	 * Get the health of an existing bar.
	 *
	 * @param player
	 *            The player whose bar's health should be returned.
	 * @return The current absolute health of the bar.<br>
	 *         If the player has no bar, this method returns -1.
	 */
	public static float getHealth(Player player) {
		if (!hasWitherBar(player))
			return -1;

		if(hasWitherBar(player))
			return getWither(player, "").health;

		return -1;
	}

	/**
	 * Get the message of an existing bar.
	 *
	 * @param player
	 *            The player whose bar's message should be returned.
	 * @return The current message displayed to the player.<br>
	 *         If the player has no bar, this method returns an empty string.
	 */
	public static String getMessage(Player player) {
		if (!hasWitherBar(player))
			return "";

		if(hasWitherBar(player))
			return getWither(player, "").name;

		return "";
	}

	private static String cleanMessage(String message) {
		if (message.length() > 64)
			message = message.substring(0, 63);

		return message;
	}

	private static void cancelTimer(Player player) {
		Integer timerID = timers.remove(player.getUniqueId());

		if (timerID != null) {
			Bukkit.getScheduler().cancelTask(timerID);
		}
	}

	private static void sendWither(FakeWither wither, Player player) {
		Util.sendPacket(player, wither.getMetaPacket(wither.getWatcher()));

		Util.sendPacket(player, wither.getTeleportPacket(player.getLocation().add(player.getEyeLocation().getDirection().normalize().multiply(30))));
	}

	private static FakeWither getWither(Player player, String message) {
		if (wither_players.containsKey(player.getUniqueId())) {
			return wither_players.get(player.getUniqueId());
		} else {
			return addWither(player, cleanMessage(message));
		}
	}

	private static FakeWither addWither(Player player, String message) {
		FakeWither wither = null;
		wither = Util.newWither(message,player.getLocation().add(player.getEyeLocation().getDirection().normalize().multiply(30)));
		Util.sendPacket(player, wither.getSpawnPacket());
		wither_players.put(player.getUniqueId(), wither);
		return wither;
	}

	private static FakeWither addWither(Player player, Location loc, String message) {
		FakeWither wither = null;
		// loc.add ?
		wither = Util.newWither(message,player.getLocation().add(player.getEyeLocation().getDirection().normalize().multiply(30)));
		Util.sendPacket(player, wither.getSpawnPacket());
		wither_players.put(player.getUniqueId(), wither);
		return wither;
	}

	public void disable() {
		plugin.getServer().getOnlinePlayers().forEach(this::quit);

		wither_players.clear();

		for (int timerID : timers.values()) {
			Bukkit.getScheduler().cancelTask(timerID);
		}

		timers.clear();
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerLoggout(PlayerQuitEvent event) {
		quit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event) {
		quit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(final PlayerTeleportEvent event) {
		handleTeleport(event.getPlayer(), event.getTo().clone());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(final PlayerRespawnEvent event) {
		handleTeleport(event.getPlayer(), event.getRespawnLocation().clone());
	}

	private void handleTeleport(final Player player, final Location loc) {

		if (!hasWitherBar(player))
			return;

		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			// Check if the player still has a dragon after the two ticks! ;)
			if (!hasWitherBar(player))
				return;

			if (wither_players.containsKey(player.getUniqueId())) {
				FakeWither oldWither = getWither(player, "");

				Util.sendPacket(player, oldWither.getTeleportPacket(loc.add(player.getEyeLocation().getDirection().normalize().multiply(30))));
			}
		}, 2L);
	}

	private void quit(Player player) {
		removeBar(player);
	}
}