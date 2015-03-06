package net.samagames.samagamesapi.database;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class FakeShardedJedisPool extends ShardedJedisPool {
	public FakeShardedJedisPool() {
		super(null, new ArrayList<JedisShardInfo>());
	}

	@Override
	public ShardedJedis getResource() {
		return new FakeSherdedRedis();
	}

	@Override
	public void returnBrokenResource(ShardedJedis resource) {
		return;
	}

	@Override
	public void returnResource(ShardedJedis resource) {
		return;
	}

	@Override
	public void initPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<ShardedJedis> factory) {
		return;
	}

	@Override
	public void returnResourceObject(ShardedJedis resource) {
		return;
	}

	@Override
	public void destroy() {
		return;
	}
}
