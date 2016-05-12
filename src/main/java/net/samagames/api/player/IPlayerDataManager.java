package net.samagames.api.player;

import net.md_5.bungee.api.chat.TextComponent;

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

	/**
	 * Kick the player from the network (need to add sanction manually)
	 *
	 * @param reason Message to show
	 *
	 */
	void kickFromNetwork(UUID player, TextComponent reason);

	/**
	 * Send a player to a specific server with the name
	 *
	 * @param server Server name
	 *
	 */
	void connectToServer(UUID player, String server);

	/**
	 * Send a message using the proxy (maybe useless ?)
	 *
	 * @param component Message
	 *
	 */
	void sendMessage(UUID player, TextComponent component);
}
