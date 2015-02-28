package net.samagames.internal;

import net.samagames.api.SamaGamesAPI;
import net.samagames.database.ConnectionDetails;
import net.samagames.database.DatabaseConnector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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

	public void onEnable() {
		log("#==========[WELCOME TO SAMAGAMES API]==========#");
		log("# SamaGamesAPI is now loading. Please read     #");
		log("# carefully all outputs coming from it.        #");
		log("#==============================================#");

		log("Loading main configuration...");
		configuration = this.getConfig();
		boolean database = configuration.getBoolean("database", true);
		serverName = configuration.getString("bungeename");
		if (serverName == null) {
			log(Level.SEVERE, "Plugin cannot load : bungeename is empty.");
			Bukkit.getServer().shutdown();
			return;
		}

		if (database) {
			File conf = new File(getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), "data.yml");
			this.getLogger().info("Searching data.yml in " + conf.getAbsolutePath());
			if (!conf.exists()) {
				log(Level.SEVERE, "Cannot find database configuration. Disabling database mode.");
				log(Level.WARNING, "Database is disabled for this session. API will work perfectly, but some plugins might have issues during run.");
				database = false;
				databaseConnector = new DatabaseConnector();
			} else {
				YamlConfiguration dataYML = YamlConfiguration.loadConfiguration(conf);
				String ip = (String) dataYML.getList("Redis-Ips").get(0);
				ConnectionDetails main = new ConnectionDetails(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]), dataYML.getString("Redis-Pass"));
				ip = dataYML.getString("rb-ip");
				ConnectionDetails bungee = new ConnectionDetails(ip.split(":")[0], Integer.parseInt(ip.split(":")[1]), dataYML.getString("Redis-Pass"));

				databaseConnector = new DatabaseConnector(main, bungee);
			}
		} else {
			log(Level.WARNING, "Database is disabled for this session. API will work perfectly, but some plugins might have issues during run.");
			databaseConnector = new DatabaseConnector();
		}



		api = new ApiImplementation(this, database);
		databaseConnector = new DatabaseConnector();
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
}
