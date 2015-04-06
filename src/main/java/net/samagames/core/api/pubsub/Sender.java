package net.samagames.core.api.pubsub;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.channels.PendingMessage;
import net.samagames.core.APIPlugin;
import net.samagames.core.database.DatabaseConnector;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class Sender implements Runnable, net.samagames.api.channels.Sender {

	private LinkedBlockingQueue<PendingMessage> pendingMessages = new LinkedBlockingQueue<>();
	private final SamaGamesAPI connector;
	private Jedis jedis;

	public Sender(SamaGamesAPI connector) {
		this.connector = connector;
	}

	public void publish(PendingMessage message) {
		pendingMessages.add(message);
	}

	@Override
	public void run() {
		fixDatabase();
		while (true) {

			PendingMessage message;
			try {
				message = pendingMessages.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				jedis.close();
				return;
			}

			boolean published = false;
			while (!published) {
				try {
					jedis.publish(message.getChannel(), message.getMessage());
					message.runAfter();
					published = true;
				} catch (Exception e) {
					fixDatabase();
				}
			}
		}
	}

	private void fixDatabase() {
		try {
			jedis = connector.getResource();
		} catch (Exception e) {
			APIPlugin.getInstance().getLogger().severe("[Publisher] Cannot connect to redis server : " + e.getMessage() + ". Retrying in 5 seconds.");
			try {
				Thread.sleep(5000);
				fixDatabase();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
