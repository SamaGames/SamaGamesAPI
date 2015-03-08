package net.samagames.core.api.player;

import net.samagames.api.player.FinancialCallback;
import net.samagames.api.player.PlayerData;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PlayerDataNoDB extends PlayerData {

	protected PlayerDataNoDB(UUID player) {
		super(player);
	}

	@Override
	public void set(String key, String value) {
		this.playerData.put(key, value);
	}

	@Override
	public void remove(String key) {
		playerData.remove(key);
	}

	@Override
	public void setInt(String key, int value) {
		set(key, String.valueOf(value));
	}

	@Override
	public void setBoolean(String key, boolean value) {
		set(key, String.valueOf(value));
	}

	@Override
	public void setDouble(String key, double value) {
		set(key, String.valueOf(value));
	}

	@Override
	public void setLong(String key, long value) {
		set(key, String.valueOf(value));
	}

	@Override
	public long increaseCoins(long incrBy) {
		setLong("coins", getCoins() + incrBy);
		return getCoins();
	}

	@Override
	public long decreaseCoins(long decrBy) {
		setLong("coins", getCoins() - decrBy);
		return getCoins();
	}

	@Override
	public void creditCoins(long amount, String reason, boolean applyMultiplier, FinancialCallback<Long> financialCallback) {
		financialCallback.done(increaseCoins(amount), amount, null);
	}

	@Override
	public void withdrawCoins(long amount, FinancialCallback<Long> financialCallback) {
		financialCallback.done(decreaseCoins(amount), -amount, null);
	}

	@Override
	public long increaseStars(long incrBy) {
		setLong("stars", getStars() + incrBy);
		return getStars();
	}

	@Override
	public long decreaseStars(long decrBy) {
		setLong("stars", getStars() - decrBy);
		return getStars();
	}

	@Override
	public void creditStars(long amount, String reason, boolean applyMultiplier, FinancialCallback<Long> financialCallback) {
		if (financialCallback != null)
			financialCallback.done(increaseStars(amount), amount, null);
	}

	@Override
	public void withdrawStars(long amount, FinancialCallback<Long> financialCallback) {
		if (financialCallback != null)
			financialCallback.done(decreaseStars(amount), -amount, null);
	}
}
