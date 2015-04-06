package net.samagames.core.api.network;

import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class RegularJoinHandler implements PacketsReceiver {

	private final JoinManagerImplement manager;

	public RegularJoinHandler(JoinManagerImplement manager) {
		this.manager = manager;
	}

	@Override
	public void receive(String channel, String packet) {
		UUID player = UUID.fromString(packet);
		manager.requestJoin(player);
	}
}
