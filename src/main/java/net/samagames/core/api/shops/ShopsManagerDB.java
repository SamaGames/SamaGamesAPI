package net.samagames.core.api.shops;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.shops.ShopsManager;
import redis.clients.jedis.ShardedJedis;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */

/*
TODO use playerdata instead
 */
public class ShopsManagerDB extends ShopsManager {

	public ShopsManagerDB(String gameType, SamaGamesAPI api) {
		super(gameType, api);
	}

	@Override
	public String getItemLevelForPlayer(UUID player, String itemCategory) {
		return api.getPlayerManager().getPlayerData(player).get("shops:"+gameType+":"+itemCategory+":current");
	}

	@Override
	public List<String> getOwnedLevels(UUID player, String itemCategory) {
		String value = api.getPlayerManager().getPlayerData(player).get("shops:" + gameType + ":" + itemCategory + ":owned");
		if (value == null)
			return null;
		return Arrays.asList(value.split(":"));
	}

	@Override
	public void addOwnedLevel(UUID player, String itemCategory, String itemName) {
		String current = api.getPlayerManager().getPlayerData(player).get("shops:" + gameType + ":" + itemCategory + ":owned");
		if (current == null)
			current = itemName;
		else {
			if (current.contains(itemName))
				return;
			current += ":" + itemName;
		}

		api.getPlayerManager().getPlayerData(player).set("shops:" + gameType + ":" + itemCategory + ":owned", current);
	}

	@Override
	public void setCurrentLevel(UUID player, String itemCategory, String itemName) {
		api.getPlayerManager().getPlayerData(player).set("shops:"+gameType+":"+itemCategory+":current", itemName);
	}
}
