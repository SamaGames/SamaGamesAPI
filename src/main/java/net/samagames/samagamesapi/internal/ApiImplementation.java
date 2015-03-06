package net.samagames.samagamesapi.internal;

import net.samagames.samagamesapi.api.SamaGamesAPI;
import net.samagames.samagamesapi.api.channels.PubSubAPI;
import net.samagames.samagamesapi.api.coins.CoinsManager;
import net.samagames.samagamesapi.api.names.UUIDTranslator;
import net.samagames.samagamesapi.api.player.PlayerDataManager;
import net.samagames.samagamesapi.api.settings.SettingsManager;
import net.samagames.samagamesapi.api.shops.ShopsManager;
import net.samagames.samagamesapi.api.stars.StarsManager;
import net.samagames.samagamesapi.api.stats.StatsManager;
import net.samagames.samagamesapi.database.DatabaseConnector;
import net.samagames.samagamesapi.internal.api.coins.CoinsManagerDB;
import net.samagames.samagamesapi.internal.api.coins.CoinsManagerNoDB;
import net.samagames.samagamesapi.internal.api.names.UUIDTranslatorDB;
import net.samagames.samagamesapi.internal.api.names.UUIDTranslatorNODB;
import net.samagames.samagamesapi.internal.api.player.PlayerDataNoDB;
import net.samagames.samagamesapi.internal.api.player.PlayerDataWithDB;
import net.samagames.samagamesapi.internal.api.pubsub.PubSubAPIDB;
import net.samagames.samagamesapi.internal.api.pubsub.PubSubNoDB;
import net.samagames.samagamesapi.internal.api.settings.SettingsManagerDB;
import net.samagames.samagamesapi.internal.api.settings.SettingsManagerNoDB;
import net.samagames.samagamesapi.internal.api.shops.ShopsManagerDB;
import net.samagames.samagamesapi.internal.api.shops.ShopsManagerNoDB;
import net.samagames.samagamesapi.internal.api.stars.StarsManagerDB;
import net.samagames.samagamesapi.internal.api.stars.StarsManagerNoDB;
import net.samagames.samagamesapi.internal.api.stats.StatsManagerDB;
import net.samagames.samagamesapi.internal.api.stats.StatsManagerNoDB;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class ApiImplementation implements SamaGamesAPI {

	protected APIPlugin plugin;
	protected boolean database;
	protected CoinsManager coinsManager;
	protected StarsManager starsManager;
	protected SettingsManager settingsManager;
	protected PlayerDataManager playerDataManager;
	protected PubSubAPI pubSub;
	protected UUIDTranslator uuidTranslator;

	public ApiImplementation(APIPlugin plugin, boolean database) {
		this.plugin = plugin;
		this.database = database;

		if (database) {
			coinsManager = new CoinsManagerDB(plugin);
			starsManager = new StarsManagerDB(plugin);
			settingsManager = new SettingsManagerDB(this);
			playerDataManager = new PlayerDataWithDB(this);
			pubSub = new PubSubAPIDB(this);
			uuidTranslator = new UUIDTranslatorDB(plugin);
		} else {
			coinsManager = new CoinsManagerNoDB(plugin);
			starsManager = new StarsManagerNoDB(plugin);
			settingsManager = new SettingsManagerNoDB();
			playerDataManager = new PlayerDataNoDB();
			pubSub = new PubSubNoDB();
			uuidTranslator = new UUIDTranslatorNODB();
		}
	}

	public ShardedJedis getResource() {
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

	@Override
	public DatabaseConnector getDatabase() {
		return plugin.databaseConnector;
	}

	@Override
	public CoinsManager getCoinsManager() {
		return coinsManager;
	}

	@Override
	public StarsManager getStarsManager() {
		return starsManager;
	}

	protected void disable() {
		if (database) {
			((PubSubAPIDB) pubSub).disable();
			plugin.databaseConnector.killConnections();
		}
	}
}
