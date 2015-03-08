package net.samagames.core.api.games;

import net.samagames.api.games.GameAPI;
import net.samagames.api.games.JoinResponse;
import net.samagames.core.APIPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PlayerListener implements Listener {

	private final GameAPI api;

	protected PlayerListener(GameAPI gameAPI) {
		this.api = gameAPI;
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		JoinResponse response;
		Player player = event.getPlayer();


	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

	}

	@EventHandler
	public void onLogout(PlayerQuitEvent event) {

	}

}
