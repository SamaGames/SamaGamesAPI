package net.samagames.samagamesapi.internal.api.pubsub;

import net.samagames.samagamesapi.api.channels.PacketsReceiver;
import net.samagames.samagamesapi.api.channels.PubSubAPI;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PubSubNoDB implements PubSubAPI {
	@Override
	public void subscribe(String channel, PacketsReceiver receiver) {
		// RIEN.
	}

	@Override
	public void send(String channel, String message) {
		// RIEN.
	}
}
