package net.samagames.tools;

import net.samagames.api.shops.IItemDescription;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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
            String nmsPotionName = itemData[1].toLowerCase();
            boolean isSplash = Boolean.parseBoolean(itemData[2]);
            boolean isLingering = Boolean.parseBoolean(itemData[3]);

            stack = MojangShitUtils.getPotion(nmsPotionName, isSplash, isLingering);

            if (itemData.length == 5 && itemData[4].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("E"))
        {
            EntityType entityType = EntityType.valueOf(itemData[1].toUpperCase());

            stack = MojangShitUtils.getMonsterEgg(entityType);

            if (itemData.length == 3 && itemData[2].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("A"))
        {
            Material material = Material.valueOf(itemData[1].toUpperCase());
            int size = Integer.parseInt(itemData[2]);

            int red = Integer.parseInt(itemData[3]);
            int green = Integer.parseInt(itemData[4]);
            int blue = Integer.parseInt(itemData[5]);

            stack = new ItemStack(material, size);

            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(Color.fromRGB(red, green, blue));
            stack.setItemMeta(meta);

            if (itemData.length == 7 && itemData[6].equalsIgnoreCase("GLOW"))
                GlowEffect.addGlow(stack);
        }
        else if (itemData[0].equalsIgnoreCase("H"))
        {
            int size = Integer.parseInt(itemData[1]);
            String username = itemData[2];

            stack = new ItemStack(Material.SKULL, size);

            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setOwner(username);
            stack.setItemMeta(meta);

            if (itemData.length == 4 && itemData[3].equalsIgnoreCase("GLOW"))
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
