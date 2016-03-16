package net.samagames.api.options;

/**
 * Server options class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IServerOptions
{
    /**
     * Get if the tab we'll be filled with ranks
     *
     * @return {@code true} if activated
     */
    boolean hasRankTabColor();

    /**
     * Set tab filling with ranks
     *
     * @param enable {@code true} to enable
     */
    void setRankTabColorEnable(boolean enable);

    /**
     * Get if the basic minecraft nature is active or not.
     *
     * @return true {@code true} if activated
     */
    boolean hasActiveNature();

    /**
     * Set if the basic minecraft nature is active.
     *
     * @param enable {@code true} to enable
     */
    void setActiveNature(boolean enable);
}