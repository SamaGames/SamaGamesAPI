package net.samagames.internal.api.stars;

import net.samagames.api.stars.StarsManager;
import net.samagames.internal.APIPlugin;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
abstract class StarsManagerBase implements StarsManager {
	protected APIPlugin plugin;

	public StarsManagerBase(APIPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void creditPlayer(UUID player, int amount, String reason) {
		creditPlayer(player, amount, reason, true);
	}

	@Override
	public void creditPlayer(final UUID player, final int amount, final String reason, final boolean applyMultiplier) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				creditPlayerSynchronized(player, amount, reason, applyMultiplier);
			}
		});
	}

	@Override
	public void creditPlayer(UUID player, int amount) {
		creditPlayer(player, amount, null, true);
	}

	@Override
	public void withdrawPlayer(final UUID player, final int amount) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				withdrawPlayer(player, amount);
			}
		});
	}
}
