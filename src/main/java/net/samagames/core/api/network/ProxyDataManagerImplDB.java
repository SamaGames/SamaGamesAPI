package net.samagames.core.api.network;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.network.ProxyDataManager;
import net.samagames.permissionsapi.database.DatabaseManager;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ProxyDataManagerImplDB implements ProxyDataManager {

	private final SamaGamesAPI api;

	public ProxyDataManagerImplDB(SamaGamesAPI api) {
		this.api = api;
	}

	@Override
	public Set<UUID> getPlayersOnServer(String server) {
		Jedis jedis = api.getBungeeResource();
		Set<String> data = jedis.smembers("connectedonserv:" + server);
		jedis.close();

		HashSet<UUID> ret = new HashSet<>();
		data.stream().forEach(str -> ret.add(UUID.fromString(str)));

		return ret;
	}

	@Override
	public Set<UUID> getPlayersOnProxy(String server) {
		Jedis jedis = api.getBungeeResource();
		Set<String> data = jedis.smembers("connected:" + server);
		jedis.close();

		HashSet<UUID> ret = new HashSet<>();
		data.stream().forEach(str -> ret.add(UUID.fromString(str)));

		return ret;
	}

	@Override
	public Map<String, String> getServers() {
		Jedis jedis = api.getBungeeResource();
		Map<String, String> servers = jedis.hgetAll("servers");
		jedis.close();

		return servers;
	}
}
