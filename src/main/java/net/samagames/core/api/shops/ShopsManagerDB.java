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
public class ShopsManagerDB extends ShopsManager {

	public ShopsManagerDB(String gameType, SamaGamesAPI api) {
		super(gameType, api);
	}

	@Override
	public String getCurrentItemForPlayer(UUID player, String itemCategory) {
		return api.getDatabase().fastGet(getKey(itemCategory, player, "current"));
	}

	@Override
	public List<String> getOwnedItems(UUID player, String itemCategory) {
		String value = api.getDatabase().fastGet(getKey(itemCategory, player, "current"));
		if (value == null)
			return null;
		return Arrays.asList(value.split(":"));
	}

	@Override
	public void addOwnedItem(UUID player, String itemCategory, String itemName) {
		String key = getKey(itemCategory, player, "owned");
		ShardedJedis jedis = api.getResource();
		String current = jedis.get(key);
		if (current == null)
			current = itemName;
		else {
			if (current.contains(itemName))
				return;
			current += ":" + itemName;
		}

		jedis.set(key, current);
		jedis.close();
	}

	@Override
	public void setCurrentItem(UUID player, String itemCategory, String itemName) {
		api.getDatabase().fastSet(getKey(itemCategory, player, "current"), itemName);
	}
}
