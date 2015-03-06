package net.samagames.samagamesapi.database;

import net.samagames.samagamesapi.internal.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.*;

import java.util.ArrayList;
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
	protected ConnexionKeeper keeper = null;
	protected BukkitTask keepTask = null;
	protected ConnectionDetails main;
	protected ConnectionDetails bungee;
	protected APIPlugin plugin;

	public DatabaseConnector(APIPlugin plugin) {
		mainPool = new FakeShardedJedisPool();
		bungeePool = new FakeJedisPool();
		this.plugin = plugin;
	}

	public DatabaseConnector(APIPlugin plugin, ConnectionDetails main, ConnectionDetails bungee) {
		this.plugin = plugin;
		setBungee(bungee);
		setMain(main);
		initiateConnections();
	}

	public ShardedJedis getResource() {
		return mainPool.getResource();
	}

	public Jedis getBungeeResource() {
		return bungeePool.getResource();
	}

	public void setBungee(ConnectionDetails bungee) {
		this.bungee = bungee;
	}

	public void setMain(ConnectionDetails main) {
		this.main = main;
	}

	public void killConnections() {
		bungeePool.destroy();
		mainPool.destroy();
	}

	public void initiateConnections() {
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

		// Init du thread
		if (keeper == null) {
			keeper = new ConnexionKeeper(plugin);
			keepTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, keeper, 3*20, 30*20);
		}
	}

	public String fastGet(String key) {
		ShardedJedis jedis = getResource();
		String val = jedis.get(key);
		jedis.close();
		return val;
	}

	public String fastSet(String key, String value) {
		ShardedJedis jedis = getResource();
		String val = jedis.set(key, value);
		jedis.close();
		return val;
	}

}
