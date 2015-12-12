package net.samagames.api.stats;

import java.util.UUID;

/**
 * Player stat class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPlayerStat
{
    /**
     * Send modifications to the database
     */
    void send();

    /**
     * Fill the statistic with fresh data
     *
     * @return {@code true} if successfully filled
     */
    boolean fill();

    /**
     * Get player's UUID
     *
     * @return Player's UUID
     */
    UUID getPlayerUUID();

    /**
     * Get statistic's game code name
     *
     * @return Game code name
     */
    String getGame();

    /**
     * Get statistic's name
     *
     * @return Name
     */
    String getStat();

    /**
     * Get statistic's value
     *
     * @return Value
     */
    double getValue();

    /**
     * Get player's rank
     *
     * @return Rank
     */
    long getRank();
}
