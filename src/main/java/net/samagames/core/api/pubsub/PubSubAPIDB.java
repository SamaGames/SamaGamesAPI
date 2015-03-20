package net.samagames.core.api.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.channels.PubSubAPI;
import net.samagames.core.APIPlugin;
import net.samagames.core.rabbitmq.RabbitConnector;

import java.io.IOException;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PubSubAPIDB implements PubSubAPI {

	private Subscriber subscriber;
	private Sender sender;
	private Channel channel;
	private Connection connection;

	public PubSubAPIDB(RabbitConnector rabbitConnector) throws IOException {
		connection = rabbitConnector.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(APIPlugin.getInstance().getServerName(), true, false, true, null);

		subscriber = new Subscriber(channel, new QueueingConsumer(channel), APIPlugin.getInstance().getServerName());
		sender = new Sender(channel);

		new Thread(subscriber).start();
		new Thread(sender).start();
	}

	@Override
	public void subscribe(String channel, PacketsReceiver receiver) {
		try {
			subscriber.registerReceiver(channel, receiver);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void subscribe(String channel, String routing, PacketsReceiver receiver) {
		try {
			subscriber.registerReceiver(channel, routing, receiver);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String channel, String message) {
		send(channel, null, message);
	}

	@Override
	public void send(String channel, String routing, String message) {
		sender.sendMessage(new UnsentMessage(channel, routing, message));
	}

	public void disable() {
		subscriber.stop();
		sender.stop();

		try {
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
