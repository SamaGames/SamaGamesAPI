package net.samagames.core.api.pubsub;

import net.samagames.api.channels.PacketsReceiver;
import net.samagames.core.APIPlugin;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class Subscriber extends JedisPubSub {

	protected HashMap<String, HashSet<PacketsReceiver>> packetsReceivers = new HashMap<>();

	public void registerReceiver(String channel, PacketsReceiver receiver) {
		if (packetsReceivers.get(channel) == null)
			packetsReceivers.put(channel, new HashSet<>());
		packetsReceivers.get(channel).add(receiver);
	}

	@Override
	public void onMessage(String channel, String message) {
		try {
			HashSet<PacketsReceiver> receivers = packetsReceivers.get(channel);
			if (receivers != null)
				receivers.forEach((PacketsReceiver receiver) -> receiver.receive(channel, message));
			else
				APIPlugin.log(Level.WARNING, "{PubSub} Received message on a channel, but no packetsReceivers were found.");
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

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
