package net.samagames.api.stats;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class StatsManager {

	protected String game;

	protected StatsManager(String game) {
		this.game = game;
	}

	public abstract void increase(UUID player, String stat, int amount);

	public abstract int getStatValue(UUID player, String stat);
}
