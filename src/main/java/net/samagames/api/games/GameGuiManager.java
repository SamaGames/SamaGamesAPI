package net.samagames.api.games;

import net.samagames.api.gui.AbstractGui;
import net.samagames.api.gui.IGuiManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
public class GameGuiManager implements IGuiManager
{
    private ConcurrentHashMap<UUID, AbstractGui> playersGui;

    /**
     * Constructor
     */
    public GameGuiManager()
    {
        this.playersGui = new ConcurrentHashMap<>();
    }

    /**
     * Open a given GUI to a given Player
     *
     * @param player The player to show the GUI
     * @param gui The AbstractGui to show
     */
    public void openGui(Player player, AbstractGui gui)
    {
        if(this.playersGui.containsKey(player.getUniqueId()))
        {
            player.closeInventory();
            this.playersGui.remove(player.getUniqueId());
        }

        this.playersGui.put(player.getUniqueId(), gui);
        gui.display(player);
    }

    /**
     * Close the opened GUI of a given Player
     *
     * @param player Player to close the GUI
     */
    public void closeGui(Player player)
    {
        if(this.playersGui.containsKey(player.getUniqueId()))
        {
            player.closeInventory();
            this.playersGui.remove(player.getUniqueId());
        }
    }

    /**
     * Remove a given Player in the opened GUIs list (remove from
     * cache)
     *
     * @param player The Player for data to remove
     */
    public void removeClosedGui(Player player)
    {
        if(this.playersGui.containsKey(player.getUniqueId()))
            this.playersGui.remove(player.getUniqueId());
    }

    /**
     * Get the opened GUI of a given Player (@see HumanEntity)
     *
     * @param player A HumanEntity
     *
     * @return The AbstractGui
     */
    @Override
    public AbstractGui getPlayerGui(HumanEntity player)
    {
        return this.getPlayerGui(player.getUniqueId());
    }

    /**
     * Get the opened GUI of a given Player (@see UUID)
     *
     * @param uuid Player UUID
     *
     * @return The AbstractGui
     */
    public AbstractGui getPlayerGui(UUID uuid)
    {
        if(this.playersGui.containsKey(uuid))
        {
            return this.playersGui.get(uuid);
        }
        else
        {
            return null;
        }
    }

    /**
     * Get the players GUIs
     *
     * @return A map with players UUID and their GUIs
     */
    @Override
    public ConcurrentHashMap<UUID, AbstractGui> getPlayersGui()
    {
        return this.playersGui;
    }
}
