package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.api.games.GameAPI;
import net.samagames.api.games.GameSignData;
import net.samagames.core.APIPlugin;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class GameApiNoDB implements GameAPI {

	protected Game game;
	protected SamaGamesAPI api;
	protected APIPlugin plugin;

	public GameApiNoDB(SamaGamesAPI api, APIPlugin plugin) {
		this.plugin = plugin;
		this.api = api;
	}

	@Override
	public void enable(String signZone, Game game) {
		this.game = game;
	}

	@Override
	public void sendSign(GameSignData data) {

	}

	@Override
	public Game getGame() {
		return game;
	}
}
