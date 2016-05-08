package net.samagames.api.shops;

import net.samagames.tools.CallBack;

/**
 * ╱╲＿＿＿＿＿＿╱╲
 * ▏╭━━╮╭━━╮▕
 * ▏┃＿＿┃┃＿＿┃▕
 * ▏┃＿▉┃┃▉＿┃▕
 * ▏╰━━╯╰━━╯▕
 * ╲╰╰╯╲╱╰╯╯╱  Created by Silvanosky on 05/05/2016
 * ╱╰╯╰╯╰╯╰╯╲
 * ▏▕╰╯╰╯╰╯▏▕
 * ▏▕╯╰╯╰╯╰▏▕
 * ╲╱╲╯╰╯╰╱╲╱
 * ＿＿╱▕▔▔▏╲＿＿
 * ＿＿▔▔＿＿▔▔＿＿
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
     * Get the selected item from a list of ids
     * @param itemsIDs list of ids
     * @return the selected ids
     * @throws Exception If the player don't have any of this items
     */
    int getSelectedItem(int[] itemsIDs) throws Exception;

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
