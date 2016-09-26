package net.samagames.tools;

import net.minecraft.server.v1_10_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MojangShitUtils
{
    public static ItemStack getPotion(String nmsPotionName, boolean splash, boolean lingering)
    {
        net.minecraft.server.v1_10_R1.ItemStack potion = new net.minecraft.server.v1_10_R1.ItemStack(splash ? Items.SPLASH_POTION : lingering ? Items.LINGERING_POTION : Items.POTION, 1);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("Potion", "minecraft:" + nmsPotionName);

        potion.setTag(tag);

        return CraftItemStack.asBukkitCopy(potion);
    }

    public static ItemStack getMonsterEgg(EntityType entityType)
    {
        net.minecraft.server.v1_10_R1.ItemStack egg = new net.minecraft.server.v1_10_R1.ItemStack(Items.SPAWN_EGG, 1);

        NBTTagCompound tag = new NBTTagCompound();

        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entityType.getName());

        tag.set("EntityTag", entityTag);

        egg.setTag(tag);

        return CraftItemStack.asBukkitCopy(egg);
    }

    public static String getEntityByMonsterEgg(ItemStack bukkitEgg)
    {
        if (bukkitEgg.getType() != Material.MONSTER_EGG)
            return null;

        net.minecraft.server.v1_10_R1.ItemStack egg = CraftItemStack.asNMSCopy(bukkitEgg);

        if (!egg.hasTag())
            return null;

        NBTTagCompound tag = egg.getTag();

        if (!tag.hasKey("EntityTag"))
            return null;

        NBTTagCompound entityTag = tag.getCompound("EntityTag");

        if (!entityTag.hasKey("id"))
            return null;

        return entityTag.getString("id");
    }
}
