package net.samagames.api.player;

import java.util.UUID;

/**
 * Player dada manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPlayerDataManager
{

	/**
	 * Get given player's data
     *
	 * @param player Player's UUID
     *
	 * @return Instance
	 */
    AbstractPlayerData getPlayerData(UUID player);

    /**
     * Get given player's data
     *
     * @param player Player's UUID
     * @param forceRefresh Refresh the data before return
     *
     * @return Instance
     */
    AbstractPlayerData getPlayerData(UUID player, boolean forceRefresh);
}
