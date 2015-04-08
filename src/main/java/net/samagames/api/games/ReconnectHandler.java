package net.samagames.api.games;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ReconnectHandler {

	void disconnect(Player player);
	void reconnect(Player player);
	boolean canReconnect(UUID player);
	void asign(IReconnectGame game);

}
