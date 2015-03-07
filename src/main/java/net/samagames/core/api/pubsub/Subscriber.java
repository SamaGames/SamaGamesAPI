package net.samagames.core.api.pubsub;

import net.samagames.api.channels.PacketsReceiver;
import net.samagames.core.APIPlugin;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.logging.Level;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class Subscriber extends JedisPubSub {

	protected HashMap<String, PacketsReceiver> packetsReceivers = new HashMap<>();

	public void registerReceiver(String channel, PacketsReceiver receiver) {
		packetsReceivers.put(channel, receiver);
	}

	@Override
	public void onMessage(String channel, String message) {
		PacketsReceiver receiver = packetsReceivers.get(channel);
		if (receiver != null)
			receiver.receive(channel, message);
		else
			APIPlugin.log(Level.WARNING, "{PubSub} Received message on a channel, but no packetsReceivers were found.");
	}

	@Override
	public void onPMessage(String s, String s1, String s2) {

	}

	@Override
	public void onSubscribe(String s, int i) {

	}

	@Override
	public void onUnsubscribe(String s, int i) {

	}

	@Override
	public void onPUnsubscribe(String s, int i) {

	}

	@Override
	public void onPSubscribe(String s, int i) {

	}
}
