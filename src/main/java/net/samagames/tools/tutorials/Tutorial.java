package net.samagames.tools.tutorials;

import net.samagames.api.SamaGamesAPI;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Tutorial implements Listener
{
	private final Plugin p;

	/**
	 * Map: player's UUID -> task executing the tutorial
	 */
	private Map<UUID, BukkitTask> viewers = new ConcurrentHashMap<>();

	/**
	 * Chapter's contents
	 */
	private List<TutorialChapter> content = new LinkedList<>();


	private long timeNeededToPlayThisTutorial = 0l;
	private Long tutorialHour = null;


	public Tutorial()
	{
		p = SamaGamesAPI.get().getPlugin();
		p.getServer().getPluginManager().registerEvents(this, p);
	}




	/* * ***  PUBLIC TUTORIAL API  *** * */


	/**
	 * Adds a chapter in the tutorial.
	 *
	 * @param chapter The chapter to add.
	 */
	public void addChapter(TutorialChapter chapter)
	{
		content.add(chapter);

		long readingTime = 0;

		for (Pair<String, Long> line : chapter.getContent())
			readingTime += line.getRight();

		timeNeededToPlayThisTutorial += readingTime;
	}

	/**
	 * The time of the day this tutorial is displayed.
	 *
	 * You can set a value above 24000 ticks if you want to play with moon phases.<br/>
	 * If {@code null}, the time is not changed. This is the default behavior.
	 *
	 * @param tutorialHour The time of the day this tutorial will be played.
	 */
	public void setTutorialHour(final Long tutorialHour)
	{
		this.tutorialHour = tutorialHour;
	}

	/**
	 * Returns this tutorial's duration.
	 *
	 * @return The duration, in ticks.
	 */
	public long getTimeNeededToPlayThisTutorial()
	{
		return timeNeededToPlayThisTutorial;
	}




	/* * ***  TUTORIAL EVENTS  *** * */


	/**
	 * This method is called when the tutorial is finished for the given player.
	 *
	 * @param player The player who just finished the tutorial. May be {@code null}.
	 * @param interrupted {@code true} if the tutorial is stopped because it was interrupted
	 *                    using {@link #stopForAll(String)}, {@link #stop(UUID)} or if the
	 *                    user disconnected.
	 */
	protected void onTutorialEnds(Player player, boolean interrupted) {}




	/* * ***  TUTORIAL LAUNCHERS AND HALTERS  *** * */


	/**
	 * Starts the tutorial for the given player.
	 *
	 * @param id The UUID of the player.
	 */
	public void start(UUID id)
	{
		if (isWatchingTutorial(id))
		{
			p.getLogger().info(p.getServer().getPlayer(id).getName() + "(" + id + ") is trying to see the tutorial whilst watching it.");
			return;
		}

		Player player = p.getServer().getPlayer(id);

		// The player cannot move anymore (except with our teleportations)
		player.setFlySpeed(0f);
		player.setAllowFlight(true);
		player.setFlying(true);

		// All other players are hidden
		for (Player other : p.getServer().getOnlinePlayers())
		{
			player.hidePlayer(other);
			other.hidePlayer(player);
		}

		// The player's hour is updated, if needed
		if(tutorialHour != null)
		{
			player.setPlayerTime(tutorialHour, false);
		}


		// The tutorial is started
		viewers.put(
				id, p.getServer().getScheduler().runTaskTimer(p, new TutorialRunner(this, id), 10L, 10L)
		);
	}


	/**
	 * Stops the tutorial for the given player.
	 *
	 * @param id The UUID of the player.
	 */
	public void stop(UUID id)
	{
		stop(id, true);
	}

	/**
	 * Stops the tutorial for everyone.
	 *
	 * @param reason A reason displayed to the viewers. Nothing sent if null.
	 */
	public void stopForAll(String reason)
	{
		for (UUID viewerID : viewers.keySet())
		{
			stop(viewerID, true);

			if(reason != null)
			{
				p.getServer().getPlayer(viewerID).sendMessage(reason);
			}
		}
	}




	/* * ***  INTERNAL API  *** * */


	/**
	 * Stops the tutorial for the given player.
	 *
	 * @param id The UUID of the player.
	 * @param interrupted {@code true} if the tutorial is stopped because it was interrupted
	 *                    using {@link #stopForAll(String)}, {@link #stop(UUID)} or if the
	 *                    user disconnected.
	 */
	void stop(UUID id, final Boolean interrupted) // package-private
	{
		if (!isWatchingTutorial(id)) return;

		Player player = p.getServer().getPlayer(id);

		if (player != null)
		{
			// The player can now move.
			player.setFlySpeed(0.1f);
			player.setFlying(false);
			player.setAllowFlight(false);

			// All other players are displayed
			for (Player other : p.getServer().getOnlinePlayers())
			{
				player.showPlayer(other);
				other.showPlayer(player);
			}

			player.resetPlayerTime();
		}

		try
		{
			viewers.get(id).cancel();
		}
		catch (IllegalStateException ignored) {}

		viewers.remove(id);

		onTutorialEnds(player, interrupted);
	}


	/**
	 * @return A list of {@link TutorialChapter}s.
	 */
	List<TutorialChapter> getContent()
	{
		return content;
	}

	/**
	 * Checks if the given player is currently watching this tutorial.
	 *
	 * @param id The player's UUID.
	 *
	 * @return {@code true} if the player is watching this tutorial.
	 */
	boolean isWatchingTutorial(UUID id)
	{
		return viewers.containsKey(id);
	}




	/* * ***  INTERNAL EVENTS  *** * */

	@EventHandler
	public void onToToggleFlight(PlayerToggleFlightEvent ev)
	{
		if(isWatchingTutorial(ev.getPlayer().getUniqueId()))
		{
			ev.setCancelled(true);
			ev.getPlayer().setFlying(true);
		}
	}

	@EventHandler
	public void onPlayerQuits(PlayerQuitEvent ev)
	{
		onPlayerQuits(ev.getPlayer());
	}

	@EventHandler
	public void onPlayerQuits(PlayerKickEvent ev)
	{
		onPlayerQuits(ev.getPlayer());
	}

	public void onPlayerQuits(Player player)
	{
		if(isWatchingTutorial(player.getUniqueId()))
		{
			stop(player.getUniqueId(), true);
		}
	}
}
