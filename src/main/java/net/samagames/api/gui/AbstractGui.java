package net.samagames.api.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.TreeMap;

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
public abstract class AbstractGui
{
    protected TreeMap<Integer, String> actions = new TreeMap<>();
    protected Inventory inventory;

    /**
     * Display and create content of the GUI with a given
     * player
     *
     * @param player Player
     */
    public abstract void display(Player player);

    /**
     * Update the content of the GUI without reopen it
     *
     * @param player Player
     */
    public void update(Player player) {}

    /**
     * Event fired when a given player close the GUI
     *
     * @param player Player
     */
    public void onClose(Player player) {}

    /**
     * Event fired when a given player click a stack {@link ItemStack}
     *
     * @param player Player
     * @param stack Stack
     * @param action Stack's defined action name
     * @param clickType Click type used
     */
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType)
    {
        this.onClick(player, stack, action);
    }

    /**
     * Event fired when a given player click a stack {@link ItemStack}
     *
     * @param player Player
     * @param stack Stack
     * @param action Stack's defined action name
     */
    public void onClick(Player player, ItemStack stack, String action) {}

    /**
     * Set data in a given slot
     *
     * @param inv GUI's inventory
     * @param name Item's display name
     * @param material Item's material {@link Material}
     * @param slot Item's slot in inventory
     * @param description Item's description
     * @param action Item's defined action name
     */
    public void setSlotData(Inventory inv, String name, Material material, int slot, String[] description, String action)
    {
        this.setSlotData(inv, name, new ItemStack(material, 1), slot, description, action);
    }

    /**
     * Set data in a given slot
     *
     * @param name Item's display name
     * @param material Item's material {@link Material}
     * @param slot Item's slot in inventory
     * @param description Item's description
     * @param action Item's defined action name
     */
    public void setSlotData(String name, Material material, int slot, String[] description, String action)
    {
        this.setSlotData(this.inventory, name, new ItemStack(material, 1), slot, description, action);
    }

    /**
     * Set data in a given slot
     *
     * @param name Item's display name
     * @param item Item {@link ItemStack}
     * @param slot ITem's slot in inventory
     * @param description Item's description
     * @param action Item's defined action name
     */
    public void setSlotData(String name, ItemStack item, int slot, String[] description, String action)
    {
        this.setSlotData(this.inventory, name, item, slot, description, action);
    }

    /**
     * Set data in a given slot
     *
     * @param inv GUI's inventory
     * @param name Item's display name
     * @param item Item {@link ItemStack}
     * @param slot ITem's slot in inventory
     * @param description Item's description
     * @param action Item's defined action name
     */
    public void setSlotData(Inventory inv, String name, ItemStack item, int slot, String[] description, String action)
    {
        this.actions.put(slot, action);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        if (description != null)
            meta.setLore(Arrays.asList(description));

        item.setItemMeta(meta);
        inv.setItem(slot, item);
    }

    /**
     * Set data in a given slot
     *
     * @param inv GUI's inventory
     * @param item Item {@link ItemStack}
     * @param slot Item's slot in inventory
     * @param action Item's defined action name
     */
    public void setSlotData(Inventory inv, ItemStack item, int slot, String action)
    {
        this.actions.put(slot, action);
        inv.setItem(slot, item);
    }

    /**
     * Set data in a given slot
     *
     * @param item Item {@link ItemStack}
     * @param slot Item's slot in inventory
     * @param action Item's defined action name
     */
    public void setSlotData(ItemStack item, int slot, String action)
    {
        setSlotData(this.inventory, item, slot, action);
    }

    /**
     * Get the defined action name of a given slot
     *
     * @param slot Slot
     *
     * @return Defined action name
     */
    public String getAction(int slot)
    {
        if (!this.actions.containsKey(slot))
            return null;

        return this.actions.get(slot);
    }

    /**
     * Get the GUI's inventory
     *
     * @return Inventory
     */
    public Inventory getInventory()
    {
        return this.inventory;
    }
}