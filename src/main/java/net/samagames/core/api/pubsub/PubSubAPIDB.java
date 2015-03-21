package net.samagames.core.api.pubsub;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.channels.PatternReceiver;
import net.samagames.api.channels.PubSubAPI;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
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
	private boolean continueSub = true;

	public PubSubAPIDB(SamaGamesAPI api) {
		this.api = api;
		subscriber = new Subscriber();
		new Thread(() -> {
			while (continueSub) {
				Jedis jedis = api.getResource();
				try {
					jedis.psubscribe(subscriber, "*");
					subscriber.registerPattern("*", APIPlugin.getInstance().getDebugListener());
				} catch (Exception e) {
					e.printStackTrace();
				}

				Bukkit.getLogger().info("Disconnected from master.");
				jedis.close();
			}
		}).start();

		Bukkit.getLogger().info("Waiting for subscribing...");
		while (!subscriber.isSubscribed())
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		Bukkit.getLogger().info("Correctly subscribed.");
	}

	@Override
	public void subscribe(String channel, PacketsReceiver receiver) {
		subscriber.registerReceiver(channel, receiver);
	}

	@Override
	public void subscribe(String pattern, PatternReceiver receiver) {
		subscriber.registerPattern(pattern, receiver);
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
		continueSub = false;
		subscriber.unsubscribe();
		try {
			Thread.sleep(500);
		} catch (Exception ignored) {}
	}
}
