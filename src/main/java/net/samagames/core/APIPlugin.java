package net.samagames.core;

import net.samagames.core.database.ConnectionDetails;
import net.samagames.core.database.DatabaseConnector;
import net.samagames.core.listeners.*;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class APIPlugin extends JavaPlugin implements Listener {

	protected static ApiImplementation api;
	protected static APIPlugin instance;
	protected DatabaseConnector databaseConnector;
	protected String serverName;
	protected FileConfiguration configuration;
	protected CopyOnWriteArraySet<String> ipWhitelist = new CopyOnWriteArraySet<>();
	protected boolean databaseEnabled;
	protected boolean allowJoin;
	protected String denyJoinReason = ChatColor.RED + "Serveur non initialisé.";
	protected boolean serverRegistered;
	protected String joinPermission = null;

	public void onEnable() {
		instance = this;

		log("#==========[WELCOME TO SAMAGAMES API]==========#");
		log("# SamaGamesAPI is now loading. Please read     #");
		log("# carefully all outputs coming from it.        #");
		log("#==============================================#");

		log("Loading main configuration...");
		this.saveDefaultConfig();
		configuration = this.getConfig();
		databaseEnabled = configuration.getBoolean("database", true);

		// Chargement de l'IPWhitelist le plus tot possible
		Bukkit.getPluginManager().registerEvents(this, this);

		serverName = configuration.getString("bungeename");
		if (serverName == null) {
			log(Level.SEVERE, "Plugin cannot load : bungeename is empty.");
			Bukkit.getServer().shutdown();
			return;
		}

		joinPermission = getConfig().getString("join-permission");

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

				Set<String> ips = ((List<String>) dataYML.getList("Redis-Ips")).stream().map(IP -> IP).collect(Collectors.toSet());

				databaseConnector = new DatabaseConnector(this, ips, dataYML.getString("Redis-Pass"), bungee);
			}
		} else {
			log(Level.WARNING, "Database is disabled for this session. API will work perfectly, but some plugins might have issues during run.");
			databaseConnector = new DatabaseConnector(this);
		}

		api = new ApiImplementation(this, databaseEnabled);

		/*
		Loading listeners
		 */

		ModerationJoinHandler moderationJoinHandler = new ModerationJoinHandler();
		api.getJoinManager().registerHandler(moderationJoinHandler, -1);
		api.getPubSub().subscribe(getServerName(), moderationJoinHandler);

		Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatFormatter(this), this);
		if (configuration.getBoolean("disable-nature", false))
			Bukkit.getPluginManager().registerEvents(new NaturalListener(), this);
		if (configuration.getBoolean("tab-colors", true))
			Bukkit.getPluginManager().registerEvents(new TabsColorsListener(this), this);

		/*
		Loading commands
		 */

		for (String command : this.getDescription().getCommands().keySet()) {
			try {
				Class clazz = Class.forName("net.samagames.core.commands.Command" + StringUtils.capitalize(command));
				Constructor ctor = clazz.getConstructor(APIPlugin.class);
				getCommand(command).setExecutor((CommandExecutor) ctor.newInstance(this));
				log("Loaded command " + command + " successfully. ");
			} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) > 3 || (calendar.get(Calendar.HOUR_OF_DAY) == 3 && calendar.get(Calendar.MINUTE) >= 45))
				calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
			calendar.set(Calendar.HOUR_OF_DAY, 3);
			calendar.set(Calendar.MINUTE, 45);
			Date sched = calendar.getTime();

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {
						int minutes = 15;
						int seconds = 01;

						@Override
						public void run() {
							seconds --;
							if (seconds < 0) {
								seconds = 59;
								minutes --;
							}

							if (minutes < 0) {
								Bukkit.getServer().shutdown();

								return;
							}

							if ((seconds == 0 && (minutes % 5 == 0 || minutes >= 3)) || (minutes == 0 && seconds % 10 == 0))
								Bukkit.broadcastMessage(ChatColor.RED + "[REBOOT] Le serveur redémarre dans " + ((minutes > 0) ? minutes + "minute" + ((minutes > 1) ? "s " : " ") : "") + ((seconds > 0) ? seconds + "seconde" + ((seconds > 1) ? "s " : " ") : ""));
						}
					}, 20L, 20L);
				}
			}, sched);
			this.getLogger().info("Scheduled automatic reboot at : " + calendar.toString());
		} catch (Exception e) {
			this.getLogger().severe("CANNOT SCHEDULE AUTOMATIC SHUTDOWN.");
			e.printStackTrace();
		}

		registerServer();
	}

	public static APIPlugin getInstance() {
		return instance;
	}

	public static ApiImplementation getApi() {
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

	public boolean isDatabaseEnabled() {
		return databaseEnabled;
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


	/*
	Listen for join
	 */

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		if (!allowJoin) {
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + denyJoinReason);
			event.setResult(PlayerLoginEvent.Result.KICK_WHITELIST);
			event.setKickMessage(ChatColor.RED + denyJoinReason);

			return;
		}

		String ip = event.getRealAddress().getHostAddress();
		if (!databaseEnabled) {
			Bukkit.getLogger().info("[WARNING] Allowing connexion without check from IP "+ip);
			return;
		}

		if (joinPermission != null && ! PermissionsBukkit.hasPermission(event.getPlayer(), joinPermission)) {
			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Vous n'avez pas la permission de rejoindre ce serveur.");
		}

		if (!ipWhitelist.contains(ip)) {
			event.setResult(PlayerLoginEvent.Result.KICK_WHITELIST);
			event.setKickMessage(ChatColor.RED + "Erreur de connexion vers le serveur... Merci de bien vouloir ré-essayer plus tard.");
			Bukkit.getLogger().info("[WARNING] An user tried to connect from IP " + event.getRealAddress().getHostAddress());
		}
	}
}
