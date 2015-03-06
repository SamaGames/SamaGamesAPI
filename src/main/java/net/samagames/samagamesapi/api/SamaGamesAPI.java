package net.samagames.samagamesapi.api;

import net.samagames.samagamesapi.api.channels.PubSubAPI;
import net.samagames.samagamesapi.api.coins.CoinsManager;
import net.samagames.samagamesapi.api.names.UUIDTranslator;
import net.samagames.samagamesapi.api.player.PlayerDataManager;
import net.samagames.samagamesapi.api.settings.SettingsManager;
import net.samagames.samagamesapi.api.shops.ShopsManager;
import net.samagames.samagamesapi.api.stars.StarsManager;
import net.samagames.samagamesapi.api.stats.StatsManager;
import net.samagames.samagamesapi.database.DatabaseConnector;
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
	public PlayerDataManager getPlayerManager();
	public PubSubAPI getPubSub();
	public UUIDTranslator getUUIDTranslator();
}
