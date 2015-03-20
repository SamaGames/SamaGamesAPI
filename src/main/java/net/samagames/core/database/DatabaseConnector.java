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
	protected JedisPool bungeePool;
	protected WhitelistRefresher keeper = null;
	protected BukkitTask keepTask = null;
	protected Set<String> main;
	protected ConnectionDetails bungee;
	protected APIPlugin plugin;
	protected String mainPassword;

	public DatabaseConnector(APIPlugin plugin) {
		mainPool = null;
		bungeePool = new FakeJedisPool();
		this.plugin = plugin;
	}

	public DatabaseConnector(APIPlugin plugin, Set<String> main, String mainPassword, ConnectionDetails bungee) {
		this.plugin = plugin;
		setBungee(bungee);
		setMain(main);
		this.mainPassword = mainPassword;
		initiateConnections();
	}

	public Jedis getResource() {
		return (mainPool == null) ? new FakeJedis() : mainPool.getResource();
	}

	public Jedis getBungeeResource() {
		return bungeePool.getResource();
	}

	public void setBungee(ConnectionDetails bungee) {
		this.bungee = bungee;
	}

	public void setMain(Set<String> main) {
		this.main = main;
	}

	public void killConnections() {
		bungeePool.destroy();
		mainPool.destroy();
	}

	public void initiateConnections() {
		// Préparation de la connexion
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1024);
		config.setMaxWaitMillis(5000);

		this.mainPool = new JedisSentinelPool("mymaster", main, config, 5000, mainPassword);

		// Connexion bungee :
		config = new JedisPoolConfig();
		config.setMaxTotal(256);
		config.setMaxWaitMillis(5000);
		bungeePool = new JedisPool(config, bungee.getHost(), bungee.getPort(), 500, bungee.getPassword());


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
