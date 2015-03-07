package net.samagames.core.api.player;

import net.samagames.api.player.PlayerDataManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PlayerDataNoDB implements PlayerDataManager {

	@Override
	public Map<String, String> getPlayerData(UUID player) {
		return getPlayerData(player, false);
	}

	@Override
	public Map<String, String> getPlayerData(UUID player, boolean forceRefresh) {
		return new HashMap<>();
	}

	@Override
	public String getData(UUID player, String data) {
		return null;
	}

	@Override
	public void setData(UUID player, String data, String value) {

	}

	@Override
	public void load(UUID player) {

	}

	@Override
	public void unload(UUID player) {

	}
}
