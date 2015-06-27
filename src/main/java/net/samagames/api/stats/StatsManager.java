package net.samagames.api.stats;

import net.samagames.core.APIPlugin;
import net.samagames.core.api.stats.Leaderboard;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class StatsManager {

	protected String game;
	protected APIPlugin plugin;

	protected StatsManager(String game, APIPlugin plugin) {
		this.game = game;
		this.plugin = plugin;
	}

	public abstract void increase(UUID player, String stat, int amount);

	public abstract void setValue(UUID player, String stat, int value);

	public abstract double getStatValue(UUID player, String stat);

	public abstract Leaderboard getLeaderboard(String stat);
}
