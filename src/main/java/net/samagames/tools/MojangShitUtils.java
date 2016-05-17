package net.samagames.tools;

import net.minecraft.server.v1_9_R2.*;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MojangShitUtils
{
    public static ItemStack getPotion(String nmsPotionName, boolean splash, boolean lingering)
    {
        net.minecraft.server.v1_9_R2.ItemStack potion = new net.minecraft.server.v1_9_R2.ItemStack(splash ? Items.SPLASH_POTION : lingering ? Items.LINGERING_POTION : Items.POTION, 1);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("Potion", "minecraft:" + nmsPotionName);

        potion.setTag(tag);

        return CraftItemStack.asBukkitCopy(potion);
    }

    public static ItemStack getMonsterEgg(EntityType entityType)
    {
        net.minecraft.server.v1_9_R2.ItemStack egg = new net.minecraft.server.v1_9_R2.ItemStack(Items.SPAWN_EGG, 1);

        NBTTagCompound tag = new NBTTagCompound();

        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entityType.getName());

        tag.set("EntityTag", entityTag);

        egg.setTag(tag);

        return CraftItemStack.asBukkitCopy(egg);
    }
}
