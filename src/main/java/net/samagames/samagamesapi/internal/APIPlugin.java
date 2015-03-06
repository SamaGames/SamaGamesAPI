package net.samagames.samagamesapi.internal;

import net.samagames.samagamesapi.api.SamaGamesAPI;
import net.samagames.samagamesapi.database.ConnectionDetails;
import net.samagames.samagamesapi.database.DatabaseConnector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class APIPlugin extends JavaPlugin {

	protected static SamaGamesAPI api;
	protected static APIPlugin instance;
	protected DatabaseConnector databaseConnector;
	protected String serverName;
	protected FileConfiguration configuration;
	protected CopyOnWriteArraySet<String> ipWhitelist;
	protected boolean databaseEnabled;
	protected boolean allowJoin;
	protected String denyJoinReason;
	protected boolean serverRegistered;

	public void onEnable() {
		log("#==========[WELCOME TO SAMAGAMES API]==========#");
		log("# SamaGamesAPI is now loading. Please read     #");
		log("# carefully all outputs coming from it.        #");
		log("#==============================================#");

		log("Loading main configuration...");
		this.saveDefaultConfig();;
		configuration = this.getConfig();
		databaseEnabled = configuration.getBoolean("database", true);
		serverName = configuration.getString("bungeename");
		if (serverName == null) {
			log(Level.SEVERE, "Plugin cannot load : bungeename is empty.");
			Bukkit.getServer().shutdown();
			return;
		}

		if (databaseEnabled) {
			File conf = new File(getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), "data.yml");
			this.getLogger().info("Searching data.yml in " + conf.getAbsolutePath());
			if (!conf.exists()) {
				log(Level.SEVERE, "Cannot find database configuration. Disabling database mode.");
				log(Level.WARNING, "Database is disabled for this session. API will work perfectly, but some plugins might have issues during run.");
				databaseEnabled = false;
				databaseConnector = new DatabaseConnector(this);
			} else {
				YamlConfiguration dataYML = YamlConfiguration.loadConfiguration(conf);
				String ip = (String) dataYML.getList("Redis-Ips").get(0);
				ConnectionDetails main = new ConnectionDetails(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]), dataYML.getString("Redis-Pass"));
				ip = dataYML.getString("rb-ip");
				ConnectionDetails bungee = new ConnectionDetails(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]), dataYML.getString("Redis-Pass"));

				databaseConnector = new DatabaseConnector(this, main, bungee);
			}
		} else {
			log(Level.WARNING, "Database is disabled for this session. API will work perfectly, but some plugins might have issues during run.");
			databaseConnector = new DatabaseConnector(this);
		}

		api = new ApiImplementation(this, databaseEnabled);
	}

	public static SamaGamesAPI getApi() {
		return api;
	}

	public static void log(String message) {
		instance.getLogger().info(message);
	}

	public static void log(Level level, String message) {
		instance.getLogger().log(level, message);
	}

	public boolean canConnect(String ip) {
		if (!databaseEnabled)
			return true;
		else
			return containsIp(ip);
	}

	public void refreshIps(List<String> ips) {
		for (String ip : ipWhitelist) {
			if (!ips.contains(ip))
				ipWhitelist.remove(ip);
		}

		for (String ip : ips) {
			if (!ipWhitelist.contains(ip))
				ipWhitelist.add(ip);
		}
	}

	public boolean containsIp(String ip) {
		return ipWhitelist.contains(ip);
	}

	public void denyJoin(String reason) {
		allowJoin = false;
		denyJoinReason = reason;
	}

	public void allowJoin() {
		allowJoin = true;
	}

	public String getServerName() {
		return serverName;
	}

	public boolean doesAllowJoin() {
		return allowJoin;
	}

	public void registerServer() {
		if (serverRegistered)
			return;

		log("Trying to register server to the proxy");
		try {
			String bungeename = getServerName();
			Jedis rb_jedis = databaseConnector.getBungeeResource();
			rb_jedis.publish("redisbungee-allservers", "StartServer::" + bungeename + "::" + this.getServer().getIp() + ":" + this.getServer().getPort());
			rb_jedis.close();

		} catch (Exception ignore) {
			return;
		}
		serverRegistered = true;
	}
}
