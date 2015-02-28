package net.samagames.internal.api.stars;

import net.samagames.internal.APIPlugin;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class StarsManagerNoDB extends StarsManagerBase {

	public StarsManagerNoDB(APIPlugin plugin) {
		super(plugin);
	}

	@Override
	public long creditPlayerSynchronized(UUID player, int amount, String reason, boolean applyMultiplier) {
		return amount;
	}

	@Override
	public long withdrawPlayerSynchronized(UUID player, int amount) {
		return amount;
	}

	@Override
	public boolean canPay(UUID player, int amount) {
		return true;
	}

	@Override
	public int getAmount(UUID player) {
		return 0;
	}
}
