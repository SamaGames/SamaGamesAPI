package net.samagames.core.database;

import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class DatabaseConnector {

	protected JedisPool mainPool;
	protected JedisPool cachePool;
	protected APIPlugin plugin;
	private RedisServer main;
	private RedisServer bungee;
	private WhitelistRefresher keeper;
	private BukkitTask keepTask;

	public DatabaseConnector(APIPlugin plugin) {
		mainPool = null;
		this.plugin = plugin;
	}

	public DatabaseConnector(APIPlugin plugin, RedisServer main, RedisServer bungee)
	{
		this.plugin = plugin;
		this.main = main;
		this.bungee = bungee;

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

		this.mainPool = new JedisPool(config, this.main.getIp(), this.main.getPort(), 5000, this.main.getPassword());
		this.cachePool = new JedisPool(config, this.bungee.getIp(), this.bungee.getPort(), 5000, this.bungee.getPassword());

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
