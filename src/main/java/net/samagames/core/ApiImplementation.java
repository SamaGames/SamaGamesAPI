package net.samagames.core;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.achievements.AchievementManager;
import net.samagames.api.channels.PubSubAPI;
import net.samagames.api.games.GameManager;
import net.samagames.api.names.UUIDTranslator;
import net.samagames.api.network.JoinManager;
import net.samagames.api.network.ProxyDataManager;
import net.samagames.api.parties.PartiesManager;
import net.samagames.api.player.PlayerDataManager;
import net.samagames.api.protocol.ProtocolManager;
import net.samagames.api.resourcepacks.ResourcePacksManager;
import net.samagames.api.settings.SettingsManager;
import net.samagames.api.shops.ShopsManager;
import net.samagames.api.stats.StatsManager;
import net.samagames.core.api.achievements.AchievementManagerImpl;
import net.samagames.core.api.games.GameManagerImpl;
import net.samagames.core.api.names.UUIDTranslatorDB;
import net.samagames.core.api.names.UUIDTranslatorNODB;
import net.samagames.core.api.network.*;
import net.samagames.core.api.parties.PartiesManagerNoDb;
import net.samagames.core.api.parties.PartiesManagerWithDB;
import net.samagames.core.api.player.PlayerDataManagerNoDB;
import net.samagames.core.api.player.PlayerDataManagerWithDB;
import net.samagames.core.api.pubsub.PubSubAPIDB;
import net.samagames.core.api.pubsub.PubSubNoDB;
import net.samagames.core.api.resourcepacks.ResourcePacksManagerImpl;
import net.samagames.core.api.settings.SettingsManagerDB;
import net.samagames.core.api.settings.SettingsManagerNoDB;
import net.samagames.core.api.shops.ShopsManagerDB;
import net.samagames.core.api.shops.ShopsManagerNoDB;
import net.samagames.core.api.stats.StatsManagerDB;
import net.samagames.core.api.stats.StatsManagerNoDB;
import net.samagames.core.database.DatabaseConnector;
import net.samagames.core.listeners.GlobalChannelHandler;
import net.samagames.api.protocol.TinyProtocol;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ApiImplementation extends SamaGamesAPI
{

	protected APIPlugin plugin;
	protected boolean database;
	protected SettingsManager settingsManager;
	protected PlayerDataManager playerDataManager;
    protected AchievementManager achievementManager;
	protected PubSubAPI pubSub;
	protected UUIDTranslator uuidTranslator;
	protected JoinManager joinManager;
	protected GameManager gameApi;
	protected ProxyDataManager proxyDataManager;
	protected PartiesManager partiesManager;
	protected ProtocolManager protocolManager = new ProtocolManager();
	protected ResourcePacksManager resourcePacksManager;

	public ApiImplementation(APIPlugin plugin, boolean database) {
		this.plugin = plugin;
		this.database = database;

		JoinManagerImplement implement = new JoinManagerImplement();
		Bukkit.getServer().getPluginManager().registerEvents(implement, plugin);
		this.joinManager = implement;

		if (database) {
			settingsManager = new SettingsManagerDB(this);
			playerDataManager = new PlayerDataManagerWithDB(this);
            achievementManager = new AchievementManagerImpl(this);
			pubSub = new PubSubAPIDB(this);
			pubSub.subscribe("global", new GlobalChannelHandler(plugin));
			pubSub.subscribe(plugin.getServerName(), new GlobalChannelHandler(plugin));

			pubSub.subscribe("commands.servers." + getServerName(), new RemoteCommandsHandler());
			pubSub.subscribe("commands.servers.all", new RemoteCommandsHandler());

			ModerationJoinHandler moderationJoinHandler = new ModerationJoinHandler(implement);
			implement.registerHandler(moderationJoinHandler, - 1);
			SamaGamesAPI.get().getPubSub().subscribe(plugin.getServerName(), moderationJoinHandler);
			pubSub.subscribe("partyjoin." + getServerName(), new PartiesPubSub(implement));
			pubSub.subscribe("join." + getServerName(), new RegularJoinHandler(implement));

			uuidTranslator = new UUIDTranslatorDB(plugin, this);
			proxyDataManager = new ProxyDataManagerImplDB(this);
			partiesManager = new PartiesManagerWithDB(this);
		} else {
			settingsManager = new SettingsManagerNoDB();
			playerDataManager = new PlayerDataManagerNoDB();
			pubSub = new PubSubNoDB();
			uuidTranslator = new UUIDTranslatorNODB();
			ModerationJoinHandler moderationJoinHandler = new ModerationJoinHandler(implement);
			implement.registerHandler(moderationJoinHandler, - 1);
			SamaGamesAPI.get().getPubSub().subscribe(plugin.getServerName(), moderationJoinHandler);
			proxyDataManager = new ProxyDataManagerImplNoDB();
			partiesManager = new PartiesManagerNoDb();
		}
	}

	@Override
	public ResourcePacksManager getResourcePacksManager() {
		if (resourcePacksManager == null)
			resourcePacksManager = new ResourcePacksManagerImpl();

		return resourcePacksManager;
	}

	@Override
	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public APIPlugin getPlugin() {
		return plugin;
	}

	public ProxyDataManager getProxyDataManager() {
		return proxyDataManager;
	}

	public GameManager getGameManager() {
		return (gameApi == null) ? (this.gameApi = new GameManagerImpl(this)) : this.gameApi;
	}

	public void replaceJoinManager(JoinManager manager) {
		this.joinManager = manager;
	}

	@Override
	public PartiesManager getPartiesManager() {
		return partiesManager;
	}

	public JoinManager getJoinManager() {
		return joinManager;
	}

	public Jedis getResource() {
		return plugin.databaseConnector.getResource();
	}

	public StatsManager getStatsManager(String game) {
		if (database)
			return new StatsManagerDB(game, plugin);
		else
			return new StatsManagerNoDB(game, plugin);
	}

	@Override
	public ShopsManager getShopsManager(String game) {
		if (database)
			return new ShopsManagerDB(game, this);
		else
			return new ShopsManagerNoDB(game, this);
	}

	@Override
	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	@Override
	public PlayerDataManager getPlayerManager() {
		return playerDataManager;
	}

    @Override
    public AchievementManager getAchievementManager()
    {
        return achievementManager;
    }

	@Override
	public PubSubAPI getPubSub() {
		return pubSub;
	}

	@Override
	public UUIDTranslator getUUIDTranslator() {
		return uuidTranslator;
	}

	public Jedis getBungeeResource() {
		return plugin.databaseConnector.getBungeeResource();
	}

	@Override
	public String getServerName() {
		return plugin.getServerName();
	}

	public DatabaseConnector getDatabase() {
		return plugin.databaseConnector;
	}

	protected void disable() {
		if (database) {
			((PubSubAPIDB) pubSub).disable();
			plugin.databaseConnector.killConnections();
		}
	}
}
