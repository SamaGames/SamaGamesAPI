package net.samagames.api;

import net.samagames.api.channels.PubSubAPI;
import net.samagames.api.coins.CoinsManager;
import net.samagames.api.names.UUIDTranslator;
import net.samagames.api.player.PlayerDataManager;
import net.samagames.api.settings.SettingsManager;
import net.samagames.api.shops.ShopsManager;
import net.samagames.api.stars.StarsManager;
import net.samagames.api.stats.StatsManager;
import net.samagames.core.database.DatabaseConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class SamaGamesAPI {

	private static SamaGamesAPI instance;

	protected SamaGamesAPI() {
		instance = this;
	}

	public static SamaGamesAPI get() {
		return instance;
	}

	public abstract ShardedJedis getResource();
	public abstract Jedis getBungeeResource();

	public abstract String getServerName();

	public abstract StatsManager getStatsManager(String game);
	public abstract ShopsManager getShopsManager(String game);
	public abstract CoinsManager getCoinsManager();
	public abstract StarsManager getStarsManager();
	public abstract SettingsManager getSettingsManager();
	public abstract PlayerDataManager getPlayerManager();
	public abstract PubSubAPI getPubSub();
	public abstract UUIDTranslator getUUIDTranslator();
}
