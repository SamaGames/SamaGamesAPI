package net.samagames.api.network;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ProxyDataManager {

	/**
	 * Return the set of all the players on a given server
	 * @param server The server you want to get players for
	 * @return A set with all players UUID
	 */
	public Set<UUID> getPlayersOnServer(String server);
	public Set<UUID> getPlayersOnProxy(String server);
	public ProxiedPlayer getProxiedPlayer(UUID uuid);

	Map<String, String> getServers();
}
