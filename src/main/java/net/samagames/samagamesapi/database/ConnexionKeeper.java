package net.samagames.samagamesapi.database;

import net.samagames.samagamesapi.api.SamaGamesAPI;
import net.samagames.samagamesapi.internal.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import redis.clients.jedis.ShardedJedis;

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
	protected SamaGamesAPI api;

	protected ConnexionKeeper(APIPlugin plugin) {
		this.plugin = plugin;
		this.api = APIPlugin.getApi();
	}

	public void run() {
		try {
			ShardedJedis jedis = api.getResource();
			List<String> whitelist = jedis.lrange("sockets:proxys", 0, - 1);
			jedis.close();

			plugin.refreshIps(whitelist);
		} catch (Exception e) {
			Bukkit.getLogger().severe("[DBCONNECT] An error occured : failed to get value.");
			e.printStackTrace();
			Bukkit.getLogger().severe("[AUTOFIX PROCESS] Trying to recreate connexion...");
			try {
				api.getDatabase().initiateConnections();
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
				api.getDatabase().fastGet("test");
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

