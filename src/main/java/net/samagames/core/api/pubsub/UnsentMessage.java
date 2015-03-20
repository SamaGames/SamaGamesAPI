package net.samagames.core.api.pubsub;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class UnsentMessage {
	public String channel;
	public String binding;
	public String message;

	public UnsentMessage(String channel, String binding, String message) {
		this.channel = channel;
		this.binding = binding;
		this.message = message;
	}

	public UnsentMessage(String channel, String message) {
		this.channel = channel;
		this.message = message;
	}
}
