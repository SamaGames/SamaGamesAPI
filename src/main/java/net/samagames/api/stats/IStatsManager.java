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
     * Increase the count of a given stat of a given
     * player
     *
     * @param player Player's UUID
     * @param stat Stat to increase
     * @param amount Amount to increase
     */
	 void increase(UUID player, String stat, int amount);

    /**
     * Set the count of a given stat of a given
     * player
     *
     * @param player Player's UUID
     * @param stat Stat to modify
     * @param value Amount to set
     */
	void setValue(UUID player, String stat, int value);

    /**
     * Get the count of a given stat of a given
     * player
     *
     * @param player Player's UUID
     * @param stat Stat to get
     *
     * @return Stat's value
     */
	double getStatValue(UUID player, String stat);

    /**
     * Get the rank of a given stat of a given
     * player
     *
     * @param player Player's UUID
     * @param stat Stat to compare
     *
     * @return Player's rank
     */
	double getRankValue(UUID player, String stat);

    /**
     * Get the leaderboard of a given stat
     *
     * @param stat Stat
     *
     * @return Leaderboard instance {@link Leaderboard}
     */
	Leaderboard getLeaderboard(String stat);
}
