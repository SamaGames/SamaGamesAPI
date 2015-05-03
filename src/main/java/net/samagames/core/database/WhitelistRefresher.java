package net.samagames.core.database;

import net.samagames.core.APIPlugin;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class WhitelistRefresher implements Runnable {

	protected APIPlugin plugin;
	protected DatabaseConnector databaseConnector;
	protected HostAndPort lastMaster;

	protected WhitelistRefresher(APIPlugin plugin, DatabaseConnector connector) {
		this.plugin = plugin;
		this.databaseConnector = connector;
	}

	public void run() {
		Jedis jedis = databaseConnector.getResource();
		Set<String> whitelist = jedis.smembers("proxys");
		jedis.close();

		plugin.refreshIps(whitelist);
	}

}

