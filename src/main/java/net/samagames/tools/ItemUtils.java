package net.samagames.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
}
