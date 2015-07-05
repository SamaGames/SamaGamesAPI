package net.samagames.api.pubsub;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IPacketsReceiver {

	public void receive(String channel, String packet);

}
