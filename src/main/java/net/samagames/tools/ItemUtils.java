package net.samagames.tools;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils
{
    public static String stackToStr(ItemStack stack)
    {
        return stack.getType().name() + ", " + stack.getDurability();
    }

    public static ItemStack strToStack(String string)
    {
        String[] data = string.split(", ");
        return new ItemStack(Material.matchMaterial(data[0]), 1, Short.valueOf(data[1]));
    }

    public static ItemStack getPlayerHead(String player)
    {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player);
        head.setItemMeta(meta);

        return head;
    }
}
