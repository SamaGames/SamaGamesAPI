package net.samagames.api.channels;

import com.rabbitmq.client.QueueingConsumer;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface PacketsReceiver {

	public void receive(QueueingConsumer.Delivery delivery);

}
