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
public class ConnexionKeeper implements Runnable {

	protected APIPlugin plugin;
	protected DatabaseConnector databaseConnector;

	protected ConnexionKeeper(APIPlugin plugin, DatabaseConnector connector) {
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
			e.printStackTrace();
			Bukkit.getLogger().severe("[AUTOFIX PROCESS] Trying to recreate connexion...");
			try {
				databaseConnector.initiateConnections();
			} catch (Exception e1) {
				Bukkit.getLogger().severe("[AUTOFIX FAILED] The database seems to be really offline...");
				e1.printStackTrace();
				plugin.denyJoin(ChatColor.RED + "Base de données innaccessible.");

				for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
					try {
						p.kickPlayer(ChatColor.RED + "Un problème s'est produit avec le serveur " + plugin.getServerName() +" sur lequel vous étiez connecté. Code erreur : DB-GONE");
					} catch (Exception ev) {
						System.out.println("[FATAL ERROR] Exception occured while trying to kick a player (ModoUtils-CheckThread.java)");
					}
				}
				return;
			}

			Bukkit.getLogger().info("Connexion created, testing...");
			try {
				databaseConnector.fastGet("test");
			} catch (Exception e2) {
				Bukkit.getLogger().severe("[AUTOFIX FAILED] The database seems to be really offline...");
				e2.printStackTrace();
				plugin.denyJoin(ChatColor.RED + "Base de données innaccessible.");

				for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
					try {
						p.kickPlayer(ChatColor.RED + "Un problème s'est produit avec le serveur " + plugin.getServerName() + " sur lequel vous étiez connecté. Code erreur : DB-GONE");
					} catch (Exception ev) {
						System.out.println("[FATAL ERROR] Exception occured while trying to kick a player (ModoUtils-CheckThread.java)");
					}
				}
				return;
			}

		}

		if (!plugin.doesAllowJoin())
			plugin.allowJoin();

		plugin.registerServer();
	}

}

