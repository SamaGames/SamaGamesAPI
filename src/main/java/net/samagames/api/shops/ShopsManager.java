package net.samagames.api.shops;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */

public abstract class ShopsManager {

    protected String gameType;
	protected SamaGamesAPI api;

	/**
	 * Ne pas utiliser ce constructeur, utiliser <code>api.getShopsManager()</code> à la place
	 * @param gameType Type du jeu
	 * @param api	   SamaGamesAPI
	 */
    public ShopsManager(String gameType, SamaGamesAPI api) {
        this.gameType = gameType;
    	this.api = api;
	}

    protected String getKey(String item, UUID id, String suffix) {
        return "shops:"+gameType+":"+item+":"+id.toString()+":"+suffix;
    }

	public String getCurrentItemForPlayer(Player player, String itemCategory) {
		return getCurrentItemForPlayer(player.getUniqueId(), itemCategory);
	}

    public abstract String getCurrentItemForPlayer(UUID player, String itemCategory);

    public abstract List<String> getOwnedItems(UUID player, String itemCategory);

    public List<String> getOwnedItems(Player player, String itemCategory) {
        return getOwnedItems(player.getUniqueId(), itemCategory);
    }

    public abstract void addOwnedItem(UUID player, String itemCategory, String itemName);

    public void addOwnedItem(Player player, String itemCategory, String itemName) {
        addOwnedItem(player.getUniqueId(), itemCategory, itemName);
    }

    public abstract void setCurrentItem(UUID player, String itemCategory, String itemName);

    public void setCurrentItem(Player player, String itemCategory, String itemName) {
        setCurrentItem(player.getUniqueId(), itemCategory, itemName);
    }
}
