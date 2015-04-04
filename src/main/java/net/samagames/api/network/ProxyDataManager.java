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

	public Set<UUID> getPlayersOnServer(String server);
	public Set<UUID> getPlayersOnProxy(String server);

	Map<String, String> getServers();
}
