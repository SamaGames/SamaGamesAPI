package net.samagames.api;

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

}
