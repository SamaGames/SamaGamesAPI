package net.samagames.core.rabbitmq;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class RabbitConnector {

	private Address[] urls;
	private ConnectionFactory factory;

	public RabbitConnector(Address[] urls, String user, String password) {
		this.urls = urls;

		factory = new ConnectionFactory();
		factory.setUsername(user);
		factory.setPassword(password);
	}

	public Connection newConnection() throws IOException {
		return factory.newConnection(urls);
	}
}
