package net.samagames.api.stats;

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
     * Get the leaderboard of a given stat
     *
     * @param stat Stat
     *
     * @return Leaderboard instance {@link Leaderboard}
     */
	Leaderboard getLeaderboard(String stat);
}
