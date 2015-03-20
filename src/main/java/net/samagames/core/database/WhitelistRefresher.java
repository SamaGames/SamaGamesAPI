package net.samagames.core.database;

import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.List;

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

	protected WhitelistRefresher(APIPlugin plugin, DatabaseConnector connector) {
		this.plugin = plugin;
		this.databaseConnector = connector;
	}

	public void run() {
		try {
			Jedis jedis = databaseConnector.getResource();
			List<String> whitelist = jedis.lrange("sockets:proxys", 0, - 1);
			jedis.close();

			plugin.refreshIps(whitelist);
		} catch (Exception e) {
			Bukkit.getLogger().severe("[DBCONNECT] An error occured : failed to get value.");
		}
	}

}

