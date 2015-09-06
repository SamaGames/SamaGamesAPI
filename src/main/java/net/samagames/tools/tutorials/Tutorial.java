package net.samagames.tools.tutorials;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <h1>Tutorial API</h1>
 *
 * <p>
 *     This API is used to display tutorials to any players.
 * </p>
 * <p>
 *     A tutorial is a set of {@code /title}s displayed to the players while they are frozen
 *     somewhere in the sky (or not). Tutorials are divided in chapters containing contents;
 *     each chapter contains a title displayed as the main {@code /title}, a location where
 *     the player is frozen during this chapter, and a list of “pages” displayed successively
 *     under that title.
 * </p>
 * <p>
 *     To use this, extend this class and use the following methods to create your tutorial.
 *     <ul>
 *         <li>
 *             {@link #addChapter(TutorialChapter)}—to add a chapter to this tutorial.
 *             Order matter!
 *         </li>
 *         <li>
 *             {@link #setReadingTime(long)}—to change the time each chapter's part is displayed.
 *         </li>
 *         <li>
 *             {@link #setTutorialHour(Long)}—to change the day of time this tutorial will be
 *             played.
 *         </li>
 *     </ul>
 * </p>
 * <p>
 *     Then, launch the tutorial using {@link #start(UUID)}.
 * </p>
 * <p>
 *     The tutorial can be force-stopped using {@link #stop(UUID)}. You can setup a callback
 *     executed when the tutorial is stopped by overriding {@link #onTutorialEnds(Player, boolean)}.
 * </p>
 *
 * @author Amaury Carrade
 */
public abstract class Tutorial
{
	private final Plugin p = SamaGamesAPI.get().getPlugin();

	/**
	 * Map: player's UUID -> task executing the tutorial
	 */
	private Map<UUID, BukkitTask> viewers = new ConcurrentHashMap<>();

	/**
	 * Chapter's contents
	 */
	private List<TutorialChapter> content = new LinkedList<>();


	private long timeNeededToPlayThisTutorial = 0l;
	private long readingTime = 50l;
	private Long tutorialHour = null;




	/* * ***  PUBLIC TUTORIAL API  *** * */


	/**
	 * Adds a chapter in the tutorial.
	 *
	 * @param chapter The chapter to add.
	 */
	public void addChapter(TutorialChapter chapter)
	{
		content.add(chapter);
		timeNeededToPlayThisTutorial += readingTime * chapter.getContent().size();
	}

	/**
	 * Sets the amount of ticks each content of the tutorial is displayed.
	 *
	 * By default, 50 ticks (2.5 seconds).
	 *
	 * @param readingTime The ticks.
	 */
	public void setReadingTime(final long readingTime)
	{
		this.readingTime = readingTime;
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
				id, p.getServer().getScheduler().runTaskTimer(p, new TutorialRunner(this, id), 20l, readingTime)
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
	 * Returns the amount of ticks each content of the tutorial is displayed.
	 *
	 * @return the amount of ticks each content of the tutorial is displayed.
	 */
	long getReadingTime()
	{
		return readingTime;
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
}
