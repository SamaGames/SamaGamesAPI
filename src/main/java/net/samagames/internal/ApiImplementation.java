package net.samagames.internal;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class ApiImplementation implements net.samagames.api.SamaGamesAPI {

	protected APIPlugin plugin;
	protected boolean database;

	public ApiImplementation(APIPlugin plugin, boolean database) {
		this.plugin = plugin;
		this.database = database;
	}

	public ShardedJedis getResource() {
		return plugin.databaseConnector.getResource();
	}

	public Jedis getBungeeResource() {
		return plugin.databaseConnector.getBungeeResource();
	}

}
