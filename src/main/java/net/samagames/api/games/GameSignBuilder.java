package net.samagames.api.games;

import net.samagames.api.signs.SignBuilder;
import org.bukkit.ChatColor;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class GameSignBuilder extends SignBuilder {

	public GameSignBuilder(String bungeeName, String gameType, IGameInfos game) {
		super(bungeeName, gameType);
		this.setSlots(game.getConnectedPlayers(), game.getTotalMaxPlayers());
		StatusEnum status = game.getStatus();
		if (status.isAllowJoin() && game.getConnectedPlayers() >= game.getTotalMaxPlayers())
			this.setStateLine(ChatColor.DARK_RED + "- Plein -");
		else if (status.isAllowJoin() && game.getConnectedPlayers() >= game.getMaxPlayers())
			this.setStateLine(ChatColor.DARK_PURPLE + "► VIP ◄");
		else
			this.setStateLine(status.getDisplay());

		this.setAllowJoin(status.isAllowJoin());
		this.setMap(game.getMapName());
	}

}
