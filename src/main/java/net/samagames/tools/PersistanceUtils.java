package net.samagames.tools;

import net.samagames.api.shops.IItemDescription;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

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
public class PersistanceUtils
{
    /**
     * @see @code{PersistanceUtils.makeStack(plugin, itemId, itemName, itemDescription)}
     */
    public static ItemStack makeStack(JavaPlugin plugin, IItemDescription itemDescription)
    {
        return makeStack(plugin, itemDescription.getItemMinecraftId(), itemDescription.getItemName(), itemDescription.getItemDesc());
    }

    /**
     * Create an ItemStack via the database data.
     *
     * The first character sets the type :
     * - B: Basic
     * - P: Potion
     * - E: Monster Egg
     * - A: Leather armor
     * - H: Player head
     *
     * Because of there are multiple way to create the different ItemStack,
     * we needed to set the first character as a type.
     *
     * To create a simple dirt block, this has to be stored like that:
     * B:DIRT:1:0
     *
     * For a strength potion: P:strength:false:false
     *
     * For a monster egg: E:OCELOT
     *
     * For a leather armor: A:CHESTPLATE:255:255:255
     *
     * For a player head: H:eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90
     * ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQ5NTY5MTczNTIxMzhjN2NmNDZmO
     * TVlOGZkOTNhODUzNzJjN2ZiMjdmOWI2MWUxZTc1MDc3MTQ0NDQ5NTMyZiJ9fX0=
     *
     * @param itemId The item's id
     * @param itemName The item's name
     * @param itemDescription The item's description
     *
     * @return An ItemStack
     */
    public static ItemStack makeStack(JavaPlugin plugin, String itemId, String itemName, String itemDescription)
    {
        String[] itemData = itemId.split(":");
        ItemStack stack;

        if (itemData[0].equalsIgnoreCase("B"))
        {
            Material material = Material.valueOf(itemData[1].toUpperCase());
            int size = Integer.parseInt(itemData[2]);
            byte durability = Byte.parseByte(itemData[3]);

            stack = new ItemStack(material, size, durability);

            if (itemData.length == 5 && itemData[4].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("P"))
        {
            String potionType = itemData[1].toUpperCase();
            boolean potionExtended = Boolean.parseBoolean(itemData[2]);
            boolean potionUpgraded = Boolean.parseBoolean(itemData[2]);
            boolean isSplash = Boolean.parseBoolean(itemData[2]);
            boolean isLingering = Boolean.parseBoolean(itemData[3]);

            stack = new ItemStack(isSplash ? Material.SPLASH_POTION : isLingering ? Material.LINGERING_POTION : Material.POTION);
            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            meta.setBasePotionData(new PotionData(PotionType.valueOf(potionType), potionExtended, potionUpgraded));
            stack.setItemMeta(meta);

            if (itemData.length == 5 && itemData[4].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("E"))
        {
            EntityType entityType = EntityType.valueOf(itemData[1].toUpperCase());

            stack = new ItemStack(Material.MONSTER_EGG, 1);
            SpawnEggMeta meta = (SpawnEggMeta) stack.getItemMeta();
            meta.setSpawnedType(entityType);
            stack.setItemMeta(meta);

            if (itemData.length == 3 && itemData[2].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("A"))
        {
            Material material = Material.valueOf("LEATHER_" + itemData[1].toUpperCase());

            int red = Integer.parseInt(itemData[2]);
            int green = Integer.parseInt(itemData[3]);
            int blue = Integer.parseInt(itemData[4]);

            stack = new ItemStack(material, 1);

            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(Color.fromRGB(red, green, blue));
            stack.setItemMeta(meta);

            if (itemData.length == 6 && itemData[5].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("H"))
        {
            stack = ItemUtils.getCustomHead(itemData[1].toUpperCase());

            if (itemData.length == 3 && itemData[2].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else
        {
            plugin.getLogger().warning("[PersistanceUtils] Failed to recover the correct item type! (" + String.join(", ", itemData) + ")");

            stack = new ItemStack(Material.DEAD_BUSH, 1);
        }

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.translateAlternateColorCodes('&', itemName));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = new ArrayList<>();

        if (itemDescription != null)
        {
            String[] lines = itemDescription.split("/n");

            for (String line : lines)
                lore.add(ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', line));
        }

        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }
}
