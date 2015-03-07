package net.samagames.api.channels;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface PubSubAPI {

	public void subscribe(String channel, PacketsReceiver receiver);

	public void send(String channel, String message);

}
