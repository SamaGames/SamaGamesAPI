package net.samagames.internal.api.coins;

import net.samagames.api.coins.CoinsManager;
import net.samagames.internal.APIPlugin;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class CoinsManagerDB extends CoinsManagerBase {

	public CoinsManagerDB(APIPlugin plugin) {
		super(plugin);
	}

	@Override
	public int creditPlayerSynchronized(UUID player, int amount, String reason, boolean applyMultiplier) {
		return 0;
	}

	@Override
	public int withdrawPlayerSynchronized(UUID player, int amount) {
		return 0;
	}

	@Override
	public boolean canPay(UUID player, int amount) {
		return false;
	}

	@Override
	public int getAmount(UUID player) {
		return 0;
	}
}
