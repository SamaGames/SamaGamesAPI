package net.samagames.core.api.shops;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.shops.ShopsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */
public class ShopsManagerNoDB extends ShopsManager {

	public ShopsManagerNoDB(String gameType, SamaGamesAPI api) {
		super(gameType, api);
	}

	@Override
	public String getCurrentItemForPlayer(UUID player, String itemCategory) {
		return null;
	}

	@Override
	public List<String> getOwnedItems(UUID player, String itemCategory) {
		return new ArrayList<>();
	}

	@Override
	public void addOwnedItem(UUID player, String itemCategory, String itemName) {
	}

	@Override
	public void setCurrentItem(UUID player, String itemCategory, String itemName) {
	}
}
