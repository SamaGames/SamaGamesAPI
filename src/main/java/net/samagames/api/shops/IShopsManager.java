package net.samagames.api.shops;

import net.samagames.api.games.GamesNames;

import java.util.UUID;

/**
 * Shops manager class
 * Created by Silvanosky
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IShopsManager
{

    /**
     * Define a game to load, by default it's false for a game server and true for hub
     * @param game The game category to load
     * @param value true to load at player join, false otherwise
     */
    void setShopToLoad(GamesNames game, boolean value);

    /**
     * Know if the game is actually loading at player join
     * @param game The game to know about
     * @return
     */
    boolean isShopLoading(GamesNames game);

    /**
     * Get the item description and data.
     * It's cached data updated every 5 min so don't worry for calls
     * @param itemID The item id to know about
     * @return The item description object
     * @throws Exception If the item does not exist in the database (call a sysadmin to create it)
     */
    IItemDescription getItemDescription(int itemID) throws Exception;

    /**
     * Get the item description and data.
     * It's cached data updated every 5 min so don't worry for calls
     * @param itemName The item name to know about
     * @return The item description object
     * @throws Exception If the item does not exist in the database (call a sysadmin to create it)
     */
    IItemDescription getItemDescriptionByName(String itemName) throws Exception;

    /**
     * The player shop loaded at join, all items for players are managed here
     * @param player The player uuid
     * @return The player shop object
     */
    IPlayerShop getPlayer(UUID player);
}
