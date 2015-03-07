package net.samagames.core.api.player;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerDataManager;
import redis.clients.jedis.ShardedJedis;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PlayerDataWithDB implements PlayerDataManager {

	public PlayerDataWithDB(SamaGamesAPI api) {
		this.api = api;
	}

	protected SamaGamesAPI api;
	protected ConcurrentHashMap<UUID, TimedPlayerDat> cachedData = new ConcurrentHashMap<>();

	@Override
	public Map<String, String> getPlayerData(UUID player) {
		return getPlayerData(player, false);
	}

	@Override
	public Map<String, String> getPlayerData(UUID player, boolean forceRefresh) {
		if (!forceRefresh && cachedData.containsKey(player)) {
			TimedPlayerDat data = cachedData.get(player);

			if ((data.getLoadedAt().getTime() + 1000 * 60 * 5) > System.currentTimeMillis()) {
				return data.getData();
			}
		}

		load(player);
		return cachedData.get(player).getData();
	}



	@Override
	public String getData(UUID player, String data) {
		if (cachedData.containsKey(player)) {
			TimedPlayerDat dat = cachedData.get(player);

			if ((dat.getLoadedAt().getTime() + 1000 * 60 * 5) > System.currentTimeMillis()) {
				return dat.getData().get(data);
			}
		}

		load(player);
		return cachedData.get(player).getData().get(data);
	}

	@Override
	public void setData(UUID player, String data, String value) {
		ShardedJedis jedis = api.getResource();
		jedis.hset("data:" + player, data, value);
		jedis.close();

		if (cachedData.containsKey(player)) {
			TimedPlayerDat dat = cachedData.get(player);

			if ((dat.getLoadedAt().getTime() + 1000 * 60 * 5) > System.currentTimeMillis()) {
				dat.getData().put(data, value);
				return;
			}
		}

		load(player);
	}

	@Override
	public void load(UUID player) {
		ShardedJedis jedis = api.getResource();
		Map<String, String> data = jedis.hgetAll("data:"+player);
		jedis.close();

		TimedPlayerDat dat = new TimedPlayerDat(new Date(), data, player);
		cachedData.put(player, dat);
	}

	@Override
	public void unload(UUID player) {
		cachedData.remove(player);
	}
}
