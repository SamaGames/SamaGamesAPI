package net.samagames.core.api.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import net.samagames.api.channels.PacketsReceiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class Sender implements Runnable {

	private final Channel channel;
	private boolean work = true;
	protected BlockingQueue<UnsentMessage> queue = new LinkedBlockingQueue<>();

	public Sender(Channel channel) throws IOException {
		this.channel = channel;
	}

	public void sendMessage(UnsentMessage message) {
		queue.add(message);
	}

	public void stop() {
		work = false;
	}

	@Override
	public void run() {
		while (work) {
			try {
				UnsentMessage message = queue.take();
				channel.exchangeDeclare(message.channel, (message.binding == null) ? "fanout" : "direct");
				channel.basicPublish(message.channel, message.binding, null, message.message.getBytes());
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
