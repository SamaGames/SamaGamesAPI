package net.samagames.internal;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.coins.CoinsManager;
import net.samagames.api.settings.SettingsManager;
import net.samagames.api.shops.ShopsManager;
import net.samagames.api.stars.StarsManager;
import net.samagames.api.stats.StatsManager;
import net.samagames.database.DatabaseConnector;
import net.samagames.internal.api.coins.CoinsManagerDB;
import net.samagames.internal.api.coins.CoinsManagerNoDB;
import net.samagames.internal.api.settings.SettingsManagerDB;
import net.samagames.internal.api.settings.SettingsManagerNoDB;
import net.samagames.internal.api.shops.ShopsManagerDB;
import net.samagames.internal.api.shops.ShopsManagerNoDB;
import net.samagames.internal.api.stars.StarsManagerDB;
import net.samagames.internal.api.stars.StarsManagerNoDB;
import net.samagames.internal.api.stats.StatsManagerDB;
import net.samagames.internal.api.stats.StatsManagerNoDB;
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

	public ApiImplementation(APIPlugin plugin, boolean database) {
		this.plugin = plugin;
		this.database = database;

		if (database) {
			coinsManager = new CoinsManagerDB(plugin);
			starsManager = new StarsManagerDB(plugin);
			settingsManager = new SettingsManagerDB(this);
		} else {
			coinsManager = new CoinsManagerNoDB(plugin);
			starsManager = new StarsManagerNoDB(plugin);
			settingsManager = new SettingsManagerNoDB();
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

}
