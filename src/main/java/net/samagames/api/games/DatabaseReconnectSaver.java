package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.core.APIPlugin;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class DatabaseReconnectSaver {

	public void addReconnectInDatabase(UUID player) {
		exec(() -> {
			Jedis jedis = SamaGamesAPI.get().getBungeeResource();
			jedis.set("rejoin:" + player.toString(), SamaGamesAPI.get().getServerName());
			jedis.close();
		});
	}

	public void addReconnectInDatabase(UUID player, int expireInSeconds) {
		exec(() -> {
			Jedis jedis = SamaGamesAPI.get().getBungeeResource();
			jedis.set("rejoin:" + player.toString(), SamaGamesAPI.get().getServerName());
			jedis.expire("rejoin:" + player.toString(), expireInSeconds);
			jedis.close();
		});
	}

	public void removeReconnectInDatabase(UUID player) {
		exec(() -> {
			Jedis jedis = SamaGamesAPI.get().getBungeeResource();
			jedis.del("rejoin:" + player.toString());
			jedis.close();
		});
	}

	private void exec(Runnable runnable) {
		APIPlugin.getInstance().getExecutor().execute(runnable);
	}

}
