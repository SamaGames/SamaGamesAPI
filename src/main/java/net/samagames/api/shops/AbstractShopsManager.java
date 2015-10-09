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

public abstract class AbstractShopsManager {

    protected String gameType;
	protected SamaGamesAPI api;

	/**
	 * Ne pas utiliser ce constructeur, utiliser <code>api.getShopsManager()</code> à la place
	 * @param gameType Type du jeu
	 * @param api	   SamaGamesAPI
	 */
    public AbstractShopsManager(String gameType, SamaGamesAPI api) {
        this.gameType = gameType;
    	this.api = api;
	}

    /**
     * Gives the item level for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @return The active item level for this player and this item
     */
	public String getItemLevelForPlayer(Player player, String item) {
		return getItemLevelForPlayer(player.getUniqueId(), item);
	}

    /**
     * Gives the item level for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @return The active item level for this player and this item
     */
    public abstract String getItemLevelForPlayer(UUID player, String item);

    /**
     * Lists the owned item levels for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @return All the item levels the user bought for this item
     */
    public abstract List<String> getOwnedLevels(UUID player, String item);

    /**
     * Lists the owned item levels for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @return All the item levels the user bought for this item
     */
    public List<String> getOwnedLevels(Player player, String item) {
        return getOwnedLevels(player.getUniqueId(), item);
    }

    /**
     * Add a level to player owned levels for this item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param itemLevel the level you want to add ("diamond" for example)
     */
    public abstract void addOwnedLevel(UUID player, String item, String itemLevel);

    /**
     * Add a level to player owned levels for this item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param itemLevel the level you want to add ("diamond" for example)
     */
    public void addOwnedLevel(Player player, String item, String itemLevel) {
        addOwnedLevel(player.getUniqueId(), item, itemLevel);
    }

    /**
     * Sets the current level for this player and item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param level the level you want to add ("diamond" for example)
     */
    public abstract void setCurrentLevel(UUID player, String item, String level);

    /**
     * Sets the current level for this player and item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param level the level you want to add ("diamond" for example)
     */
    public void setCurrentLevel(Player player, String item, String level) {
        setCurrentLevel(player.getUniqueId(), item, level);
    }

    public abstract void resetLevel(UUID uuid, String item);

    public void resetLevel(Player player, String item) {
        resetLevel(player.getUniqueId(), item);
    }
}
