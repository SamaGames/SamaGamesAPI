package net.samagames.api.shops;

import net.samagames.api.stats.IStatsManager;

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

    void setShopToLoad(IStatsManager.StatsNames game, boolean value);

    boolean isShopLoading(IStatsManager.StatsNames game);

    IItemDescription getItemDescription(int itemID) throws Exception;

    IPlayerShop getPlayer(UUID player);
}
