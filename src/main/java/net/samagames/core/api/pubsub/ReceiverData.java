package net.samagames.core.api.pubsub;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import net.samagames.api.channels.PacketsReceiver;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class ReceiverData {
	public String channel;
	public String routingKey;
	public PacketsReceiver receiver;

	public void execute(QueueingConsumer.Delivery delivery) {
		Envelope envelope = delivery.getEnvelope();
		if (envelope.getExchange().equals(channel) && (routingKey == null || routingKey.equals(envelope.getRoutingKey()))) {
			receiver.receive(delivery);
		}
	}

	public ReceiverData(String channel, String routingKey, PacketsReceiver receiver) {
		this.channel = channel;
		this.routingKey = routingKey;
		this.receiver = receiver;
	}

	public ReceiverData(String channel, PacketsReceiver receiver) {
		this(channel, null, receiver);
	}
}
