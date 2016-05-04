package net.samagames.api.shops;

import net.samagames.api.stats.IStatsManager;

import java.util.List;
import java.util.UUID;

/**
 * Shops manager class
 * Created by Silvanosky
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IShopsManager
{
//TODO
    /**
     * Gives the item level for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     *
     * @return The active item level for this player and this item
     */
    String getItemLevelForPlayer(UUID player, String item);

    /**
     * Lists the owned item levels for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     *
     * @return All the item levels the user bought for this item
     */
    public abstract List<String> getOwnedLevels(UUID player, String item);

    /**
     * Add a level to player owned levels for this item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param itemLevel the level you want to add ("diamond" for example)
     */
    public abstract void addOwnedLevel(UUID player, String item, String itemLevel);

    /**
     * Sets the current level for this player and item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param level the level you want to add ("diamond" for example)
     */
    public abstract void setCurrentLevel(UUID player, String item, String level);

    /**
     * Reset the current level for this player and item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param uuid The player you are querying
     * @param item The item you are querying ("hoe" for example)
     */
    public abstract void resetLevel(UUID uuid, String item);


    /**
     * Sets the current level for this player and item
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     * @param level the level you want to add ("diamond" for example)
     */

    /**
     * Gives the item level for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     *
     * @return The active item level for this player and this item
     */


    /**
     * Lists the owned item levels for a specified item and a specified player
     * The level of an item is, for example "diamond" for the quake "hoe" item
     *
     * @param player The player you are querying
     * @param item The item you are querying ("hoe" for example)
     *
     * @return All the item levels the user bought for this item
     */

    void setShopToLoad(IStatsManager.StatsNames game, boolean value);

    boolean isShopLoading(IStatsManager.StatsNames game);

    IItemDescription getItemDescription(int itemID) throws Exception;

    IPlayerShop getPlayer(UUID player);
}
