package net.samagames.core.api.pubsub;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.channels.PubSubAPI;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PubSubAPIDB implements PubSubAPI {

	private SamaGamesAPI api;
	private Subscriber subscriber;

	public PubSubAPIDB(SamaGamesAPI api) {
		this.api = api;
		subscriber = new Subscriber();
	}

	@Override
	public void subscribe(String channel, PacketsReceiver receiver) {
		new Thread(() -> {
			Jedis jedis = api.getResource();

			subscriber.registerReceiver(channel, receiver);
			jedis.subscribe(subscriber, channel);

			jedis.close();
		}).start();
	}

	@Override
	public void send(String channel, String message) {
		new Thread(() -> {
			Jedis jedis = api.getResource();

			jedis.publish(channel, message);

			jedis.close();
		}).start();
	}

	public void disable() {
		subscriber.unsubscribe();
		try {
			Thread.sleep(500);
		} catch (Exception ignored) {}
	}
}
