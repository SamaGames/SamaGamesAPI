package net.samagames.api.gameapi;

import net.samagames.api.signs.SignMaker;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class GameSignMaker extends SignMaker {

	private Game game;

	public GameSignMaker(String bungeeName, String gameType, Game game) {
		super(bungeeName, gameType);
		this.game = game;
		this.setSlotsLine(game.getConnectedPlayers() + "/" + game.getTotalMaxPlayers());
	}

}
