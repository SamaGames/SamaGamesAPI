package net.samagames.api.stats;

import net.samagames.api.stats.games.*;

import java.util.UUID;

/**
 * Player stat class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPlayerStats
{
    /**
     * Send modifications to the database
     */
    void updateStats();

    /**
     * Get the statistics with fresh data
     *
     * @return {@code true} if successfully filled
     */
    boolean refreshStats();

    /**
     * Get player's UUID
     *
     * @return Player's UUID
     */
    UUID getPlayerUUID();

    /**
     * Get Dimension stats for player
     *
     * @return Player's UUID
     */
    IDimensionStats getDimensionStats();

    /**
     * Get HeroBattle stats for player
     *
     * @return Player's UUID
     */
    IHeroBattleStats getHeroBattleStats();

    /**
     * Get JukeBox stats for player
     *
     * @return Player's UUID
     */
    IJukeBoxStats getJukeBoxStats();

    /**
     * Get Quake stats for player
     *
     * @return Player's UUID
     */
    IQuakeStats getQuakeStats();

    /**
     * Get UHCRun stats for player
     *
     * @return Player's UUID
     */
    IUHCRunStats getUHCRunStats();

    /**
     * Get Uppervoid stats for player
     *
     * @return Player's UUID
     */
    IUppervoidStats getUppervoidStats();
}
