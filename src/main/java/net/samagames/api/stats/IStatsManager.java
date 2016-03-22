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

    enum StatsNames{
        GLOBAL(0),
        DIMENSION(1),
        HEROBATTLE(2),
        JUKEBOX(3),
        QUAKE(4),
        UHCRUN(5),
        UPPERVOID(6);

        private int value;
        StatsNames(int value)
        {
            this.value = value;
        }

        public int intValue()
        {
            return value;
        }
    }

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
     * @param game Select game
     * @param stat Stat
     *
     * @return Leaderboard instance {@link Leaderboard}
     */
	Leaderboard getLeaderboard(StatsNames game, String stat);
}
