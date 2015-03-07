package net.samagames.core.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.core.APIPlugin;
import org.bukkit.event.Listener;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class APIListener implements Listener {
	protected APIPlugin plugin;
	protected SamaGamesAPI api;

	public APIListener(APIPlugin plugin) {
		this.plugin = plugin;
		this.api = APIPlugin.getApi();
	}
}
