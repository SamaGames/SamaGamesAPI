package net.samagames.core.api.stats;

import net.samagames.api.stats.StatsManager;
import net.samagames.core.APIPlugin;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class StatsManagerNoDB extends StatsManager {

	public StatsManagerNoDB(String game, APIPlugin plugin) {
		super(game, plugin);
	}

	@Override
	public void increase(final UUID player, final String stat, final int amount) {
	}

	@Override
	public void setValue(UUID player, String stat, int value) {

	}

	@Override
	public double getStatValue(UUID player, String stat) {
		return 0D;
	}

	@Override
	public Leaderboard getLeaderboard(String stat)
	{
		return null;
	}
}
