package net.samagames.api.stats;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class AbstractStatsManager {

	protected String game;

	protected AbstractStatsManager(String game) {
		this.game = game;
	}

	public abstract void increase(UUID player, String stat, int amount);

	public abstract void setValue(UUID player, String stat, int value);

	public abstract double getStatValue(UUID player, String stat);

	public abstract double getRankValue(UUID player, String stat);

	public abstract Leaderboard getLeaderboard(String stat);

	public abstract void clearCache();

	public abstract void finish();
}
