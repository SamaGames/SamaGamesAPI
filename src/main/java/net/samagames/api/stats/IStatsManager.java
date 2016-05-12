package net.samagames.api.stats;

import net.samagames.api.games.GamesNames;

import java.util.UUID;

/**
 * Stats manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IStatsManager
{

    void finish();
    void clearCache();

    /**
     * Get the player cached stats instance
     * player
     *
     *
     * @return IPlayerStats with all stats loaded
     */
    IPlayerStats getPlayerStats(UUID player);

    /**
     * Define if a game will be loaded at player join
     * @param game The game wanted
     * @param value The value wanted (true to set auto load for the specified games, false to disable autoload)
     */
    void setStatsToLoad(GamesNames game, boolean value);

    /**
     * Know if a game is already loaded by the manager
     * @param game The wanted game
     * @return
     */
    boolean isStatsLoading(GamesNames game);

    /**
     * Get the leaderboard of a given stat
     *
     * @param game Select game
     * @param stat Stat
     *
     * @return Leaderboard instance {@link Leaderboard}
     */
	Leaderboard getLeaderboard(GamesNames game, String stat);
}
