package net.samagames.api.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Geekpower14 on 10/08/2015.
 */
public interface IGuiManager {

    /**
     * Open the given Abstract GUI to the player
     *
     * @param player The player to show the gui
     * @param gui The AbstractGui to show
     */
    void openGui(Player player, AbstractGui gui);

    /**
     * Close the opened gui of the player
     *
     * @param player Player to close the gui
     */
    void closeGui(Player player);

    /**
     * Remove the Gui data of the player from the save list
     * Automatically called in closeGui(Player player)
     *
     * @param player The Player for data to remove
     */
    void removeClosedGui(Player player);

    /**
     * Get the Gui object of a player
     *
     * @param player The HumanPlayer object to get the gui
     * @return The AbstractGui of the player
     */
    AbstractGui getPlayerGui(HumanEntity player);

    /**
     * Get the Gui object of a player
     *
     * @param player The UUID of the player to get the gui
     * @return The AbstractGui of the player
     */
    AbstractGui getPlayerGui(UUID player);

    /**
     * Get all polayers who have a Gui opened
     *
     * @return The gui list and uuid of players who opened the gui
     */
    ConcurrentHashMap<UUID, AbstractGui> getPlayersGui();
}
