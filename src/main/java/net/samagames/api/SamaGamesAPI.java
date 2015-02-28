package net.samagames.api;

import net.samagames.api.coins.CoinsManager;
import net.samagames.api.settings.SettingsManager;
import net.samagames.api.shops.ShopsManager;
import net.samagames.api.stars.StarsManager;
import net.samagames.api.stats.StatsManager;
import net.samagames.database.DatabaseConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface SamaGamesAPI {

	public ShardedJedis getResource();
	public Jedis getBungeeResource();
	public DatabaseConnector getDatabase();

	public String getServerName();

	public StatsManager getStatsManager(String game);
	public ShopsManager getShopsManager(String game);
	public CoinsManager getCoinsManager();
	public StarsManager getStarsManager();
	public SettingsManager getSettingsManager();
}
