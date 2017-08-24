package net.samagames.api.shops;

import net.samagames.tools.CallBack;

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
public interface IPlayerShop {

    /**
     * Refresh all data from the database
     * There is a cooldown of 5 min and this is called sync
     */
    void refresh();

    /**
     * Add an item with transaction to player
     * @param itemID id of the item
     * @param priceCoins the paid price in coins of the transaction
     * @param priceStars the paid price in stars of the transaction
     * @param selected is this item selected (you need to unselect other item if there is a hierarchy)
     * @return
     */
    void addItem(int itemID, int priceCoins, int priceStars, boolean selected);

    /**
     * Add an item with transaction to player
     * @param itemID id of the item
     * @param priceCoins the paid price in coins of the transaction
     * @param priceStars the paid price in stars of the transaction
     * @param selected is this item selected (you need to unselect other item if there is a hierarchy)
     * @param callBack The callback executed at the end of the database update
     * @return
     */
    void addItem(int itemID, int priceCoins, int priceStars, boolean selected, CallBack<Boolean> callBack);

    /**
     * Set the item as selected
     * @param itemID the id of item to change selected state
     * @param selected the select state
     * @throws Exception If the player don't have this item
     */
    void setSelectedItem(int itemID, boolean selected) throws Exception;

    /**
     * Get a selected item from a list of item ids
     * @param itemsIDs list of ids
     * @return the id from the list who is selected
     * @throws Exception If the player don't have any of this items
     */
    int getSelectedItemFromList(int[] itemsIDs) throws Exception;

    /**
     *  Get the item selection state
     * @param itemID The item to know about
     * @return true if selected, false otherwise
     * @throws Exception If the player don't have this item
     */
    boolean isSelectedItem(int itemID) throws Exception;

    /**
     * Get the transaction of an item
     * @param itemID The item to know about
     * @return null if the player don't have the item, ItemTransaction otherwise
     */
    ITransaction getTransactionsByID(int itemID);
}
