package net.samagames.api.shops;

import net.samagames.api.games.GamesNames;

import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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
