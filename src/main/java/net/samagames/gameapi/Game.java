package net.samagames.gameapi;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface Game {

	public JoinResponse preJoinPlayer(UUID player, JoinResponse response);
	public JoinResponse joinPlayer(Player player, JoinResponse response);
	public void logout(Player player);

}
