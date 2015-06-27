package net.samagames.core.api.network;

import com.google.gson.Gson;
import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.network.ProxiedPlayer;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ProxiedPlayerDB implements ProxiedPlayer {

	private final UUID playerId;

	public ProxiedPlayerDB(UUID playerId) {
		this.playerId = playerId;
	}

	@Override
	public String getServer() {
		return SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId).get("currentserver", "Inconnu");
	}

	@Override
	public String getProxy() {
		return SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId).get("currentproxy", "Inconnu");
	}

    @Override
    public String getIp() {
        return SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId).get("currentip", "0.0.0.0");
    }

	@Override
	public void disconnect(TextComponent reason) {
		SamaGamesAPI.get().getPubSub().send("apiexec.kick", playerId + " " + new Gson().toJson(reason));
	}

	@Override
	public void connect(String server) {
		SamaGamesAPI.get().getPubSub().send("apiexec.connect", playerId + " " + server);
	}

	@Override
	public void connectGame(String game) {
		SamaGamesAPI.get().getPubSub().send("join." + game, playerId + "");
	}

	@Override
	public void sendMessage(TextComponent component) {
		SamaGamesAPI.get().getPubSub().send("apiexec.message", playerId + " " + new Gson().toJson(component));
	}
}
