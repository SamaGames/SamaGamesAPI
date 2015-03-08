package net.samagames.api;

import net.samagames.api.channels.PubSubAPI;
import net.samagames.api.games.GameAPI;
import net.samagames.api.names.UUIDTranslator;
import net.samagames.api.player.PlayerDataManager;
import net.samagames.api.settings.SettingsManager;
import net.samagames.api.shops.ShopsManager;
import net.samagames.api.stats.StatsManager;
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

	/**
	 * This method returns a ShardedJedis resource, which allows the user to do operations into the database.
	 * The connexion returned MUST be closed using <code>ShardedJedis.close()</code>
	 * @return A shardedJedis database connexion
	 */
	public abstract ShardedJedis getResource();

	/**
	 * This method returns a Jedis object, representing a connexion to the proxies database. This database mainly contains data from redisbungee.
	 * Don't forget to close the connexion after use
	 * @return A database connexion
	 */
	public abstract Jedis getBungeeResource();

	public abstract String getServerName();

	public abstract StatsManager getStatsManager(String game);
	public abstract ShopsManager getShopsManager(String game);
	public abstract SettingsManager getSettingsManager();
	public abstract PlayerDataManager getPlayerManager();
	public abstract PubSubAPI getPubSub();
	public abstract UUIDTranslator getUUIDTranslator();
	public abstract GameAPI getGameAPI();

}
