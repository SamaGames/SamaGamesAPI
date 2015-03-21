package net.samagames.core.database;

import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.*;

import java.util.Set;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class DatabaseConnector {

	protected JedisSentinelPool mainPool;
	protected JedisSentinelPool cachePool;
	protected Set<String> sentinels;
	protected APIPlugin plugin;
	protected String password;
	protected String masterName;
	protected String cacheName;
	private WhitelistRefresher keeper;
	private BukkitTask keepTask;

	public DatabaseConnector(APIPlugin plugin) {
		mainPool = null;
		cachePool = null;
		this.plugin = plugin;
	}

	public DatabaseConnector(APIPlugin plugin, Set<String> main, String masterName, String cacheName, String mainPassword) {
		this.plugin = plugin;
		this.sentinels = main;
		this.masterName = masterName;
		this.cacheName = cacheName;
		this.password = mainPassword;

		initiateConnections();
	}

	public Jedis getResource() {
		return (mainPool == null) ? new FakeJedis() : mainPool.getResource();
	}

	public Jedis getBungeeResource() {
		return (cachePool == null) ? new FakeJedis() : cachePool.getResource();
	}

	public void killConnections() {
		cachePool.destroy();
		mainPool.destroy();
	}

	public void initiateConnections() {
		// Préparation de la connexion
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1024);
		config.setMaxWaitMillis(5000);

		this.mainPool = new JedisSentinelPool(masterName, sentinels, config, 5000, password);
		this.cachePool = new JedisSentinelPool(cacheName, sentinels, config, 5000, password);

		// Init du thread

		if (keeper == null) {
			keeper = new WhitelistRefresher(plugin, this);
			keepTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, keeper, 3*20, 30*20);
		}
	}

	protected String fastGet(String key) {
		Jedis jedis = getResource();
		String val = jedis.get(key);
		jedis.close();
		return val;
	}

}
