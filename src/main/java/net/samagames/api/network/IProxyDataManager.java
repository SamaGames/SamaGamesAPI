package net.samagames.api.network;

import java.util.Set;
import java.util.UUID;

/**
 * Proxy data manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IProxyDataManager
{
    /**
     * Execute a command on all proxies
     *
     * @param command Command
     * @param args Command's arguments
     */
	void apiexec(String command, String... args);

	/**
	 * Return the set of all the players on a given server
     *
	 * @param server The server
     *
	 * @return A set of UUID
	 */
	Set<UUID> getPlayersOnServer(String server);

    /**
     * Return the set of all the players on a given proxy
     *
     * @param proxy The proxy
     *
     * @return A set of UUID
     */
	Set<UUID> getPlayersOnProxy(String proxy);

    /**
     * Get a {@link IProxiedPlayer} instance of a given
     * UUID filled with needed information
     *
     * @param uuid Player's UUID
     *
     * @return Instance
     */
	IProxiedPlayer getProxiedPlayer(UUID uuid);
}
