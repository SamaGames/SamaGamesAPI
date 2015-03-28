package net.samagames.core.database;

import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

import java.util.List;
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
		try {
			HostAndPort master = databaseConnector.mainPool.getCurrentHostMaster();
			if (lastMaster == null && master != null) {
				Bukkit.getLogger().info("Connected to master : " + master.getHost() + ":" + master.getPort());
				this.lastMaster = master;
			} else if (master != null && !master.equals(lastMaster)) {
				Bukkit.getLogger().info("Switched master : " + lastMaster.getHost() + ":" + lastMaster.getPort() + " -> " + master.getHost() + ":" + master.getPort());
				this.lastMaster = master;
			}

			Jedis jedis = databaseConnector.getResource();
			Set<String> whitelist = jedis.smembers("proxys");
			jedis.close();

			plugin.refreshIps(whitelist);
		} catch (Exception e) {
			Bukkit.getLogger().severe("[DBCONNECT] An error occured : failed to get value. (" + e.getMessage() + ")");
		}
	}

}

