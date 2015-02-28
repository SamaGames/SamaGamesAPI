package net.samagames.database;

import net.samagames.permissionsapi.database.Database;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class DatabaseConnector {

	protected ShardedJedisPool mainPool;
	protected JedisPool bungeePool;

	public DatabaseConnector() {
		mainPool = new FakeShardedJedisPool();
		bungeePool = new FakeJedisPool();
	}

	public DatabaseConnector(ConnectionDetails main, ConnectionDetails bungee) {
		initiateConnections(main, bungee);
	}

	public ShardedJedis getResource() {
		return mainPool.getResource();
	}

	public Jedis getBungeeResource() {
		return bungeePool.getResource();
	}

	public void initiateConnections(ConnectionDetails main, ConnectionDetails bungee) {
		// Préparation de la connexion

		JedisShardInfo shard = new JedisShardInfo(main.getHost(), main.getPort());
		shard.setPassword(main.getPassword());
		List<JedisShardInfo> shards = new ArrayList<>();
		shards.add(shard);

		// Initialisation de la connexion

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1024);
		config.setMaxWaitMillis(5000);
		mainPool = new ShardedJedisPool(config, shards);


		// Connexion bungee :
		config = new JedisPoolConfig();
		config.setMaxTotal(256);
		config.setMaxWaitMillis(5000);
		bungeePool = new JedisPool(config, bungee.getHost(), bungee.getPort(), 500, bungee.getPassword());
	}

}
