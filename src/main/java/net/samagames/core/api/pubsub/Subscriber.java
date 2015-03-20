package net.samagames.core.api.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import net.samagames.api.channels.PacketsReceiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class Subscriber implements Runnable {

	protected HashMap<String, HashSet<ReceiverData>> packetsReceivers = new HashMap<>();
	protected QueueingConsumer consumer;
	protected Channel channel;
	protected String queueName;
	private boolean work = true;

	public Subscriber(Channel channel, QueueingConsumer consumer, String queueName) throws IOException {
		this.channel = channel;
		this.consumer = consumer;
		this.queueName = queueName;

		this.channel.basicConsume(queueName, true, consumer);
	}

	public void stop() {
		work = false;
	}

	public void registerReceiver(String channel, PacketsReceiver receiver) throws IOException {
		HashSet<ReceiverData> receivers = packetsReceivers.get(channel);
		if (receivers == null)
			receivers = new HashSet<>();
		receivers.add(new ReceiverData(channel, receiver));
		packetsReceivers.put(channel, receivers);

		this.channel.exchangeDeclare(queueName, "fanout");
		this.channel.queueBind(queueName, channel, "");
	}

	public void registerReceiver(String channel, String routing, PacketsReceiver receiver) throws IOException {
		HashSet<ReceiverData> receivers = packetsReceivers.get(channel);
		if (receivers == null)
			receivers = new HashSet<>();
		receivers.add(new ReceiverData(channel, routing, receiver));
		packetsReceivers.put(channel, receivers);

		this.channel.exchangeDeclare(queueName, "direct");
		this.channel.queueBind(queueName, channel, routing);
	}

	@Override
	public void run() {
		while (work) {
			try {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String channel = delivery.getEnvelope().getExchange();
				HashSet<ReceiverData> receivers = packetsReceivers.get(channel);
				if (receivers != null)
					receivers.forEach((ReceiverData receiver) -> receiver.execute(delivery));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
