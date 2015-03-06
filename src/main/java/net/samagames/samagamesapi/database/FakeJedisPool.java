package net.samagames.samagamesapi.database;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;


/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class FakeJedisPool extends JedisPool {
	public FakeJedisPool() {
		super("", 0);
	}

	@Override
	public Jedis getResource() {
		return new FakeJedis();
	}

	@Override
	public void returnBrokenResource(Jedis resource) {
	}

	@Override
	public void returnResource(Jedis resource) {
	}

	@Override
	public void initPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<Jedis> factory) {
	}

	@Override
	public void returnResourceObject(Jedis resource) {
	}

	@Override
	public void destroy() {
	}
}
