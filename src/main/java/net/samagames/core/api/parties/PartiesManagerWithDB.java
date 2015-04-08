package net.samagames.core.api.parties;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.parties.PartiesManager;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PartiesManagerWithDB implements PartiesManager {

	private final SamaGamesAPI api;

	public PartiesManagerWithDB(SamaGamesAPI api) {
		this.api = api;
	}

	@Override
	public UUID getPlayerParty(UUID player) {
		Jedis jedis = api.getBungeeResource();
		String val = jedis.get("currentparty:" + player);
		jedis.close();
		return (val != null) ? UUID.fromString(val) : null;
	}

	@Override
	public HashMap<UUID, String> getPlayersInParty(UUID party) {
		Jedis jedis = api.getBungeeResource();
		Map<String, String> data = jedis.hgetAll("party:" + party + ":members");
		jedis.close();

		if (data == null)
			return new HashMap<>();

		HashMap<UUID, String> ret = new HashMap<>();
		data.entrySet().forEach(entry -> ret.put(UUID.fromString(entry.getKey()), entry.getValue()));

		return ret;
	}

	@Override
	public String getCurrentServer(UUID party) {
		Jedis jedis = api.getBungeeResource();
		String server = jedis.get("party:" + party + ":server");
		jedis.close();
		return server;
	}

	@Override
	public UUID getLeader(UUID party) {
		Jedis jedis = api.getBungeeResource();
		String leader = jedis.get("party:" + party + ":lead");
		jedis.close();
		return UUID.fromString(leader);
	}
}
