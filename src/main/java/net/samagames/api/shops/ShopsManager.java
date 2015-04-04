package net.samagames.api.shops;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
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

	public String getItemLevelForPlayer(Player player, String item) {
		return getItemLevelForPlayer(player.getUniqueId(), item);
	}

    public abstract String getItemLevelForPlayer(UUID player, String item);

    public abstract List<String> getOwnedLevels(UUID player, String item);

    public List<String> getOwnedLevels(Player player, String item) {
        return getOwnedLevels(player.getUniqueId(), item);
    }

    public abstract void addOwnedLevel(UUID player, String item, String itemLevel);

    public void addOwnedLevel(Player player, String item, String itemLevel) {
        addOwnedLevel(player.getUniqueId(), item, itemLevel);
    }

    public abstract void setCurrentLevel(UUID player, String item, String level);

    public void setCurrentLevel(Player player, String item, String level) {
        setCurrentLevel(player.getUniqueId(), item, level);
    }
}
