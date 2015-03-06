package net.samagames.samagamesapi.internal.api.proxys;

import net.samagames.samagamesapi.api.SamaGamesAPI;
import net.samagames.samagamesapi.api.proxys.ProxyDataManager;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ProxyDataManagerDB implements ProxyDataManager {

	private final SamaGamesAPI api;

	public ProxyDataManagerDB(SamaGamesAPI api) {
		this.api = api;
	}

	@Override
	public Map<String, String> getPlayerData(UUID player) {
		Jedis jedis = api.getBungeeResource();
		Map<String, String> data = jedis.hgetAll("player:"+player);
		jedis.close();
		return data;
	}

	private String getDataKey(UUID playerID, String key) {
		Map<String, String> data = getPlayerData(playerID);
		if (data != null && data.containsKey(key))
			return data.get(key);
		return null;
	}

	@Override
	public String getPlayerServer(UUID player) {
		return getDataKey(player, "server");
	}

	@Override
	public String getPlayerProxy(UUID player) {
		return getDataKey(player, "proxy");
	}

	@Override
	public String getPlayerIP(UUID player) {
		return getDataKey(player, "ip");
	}
}
