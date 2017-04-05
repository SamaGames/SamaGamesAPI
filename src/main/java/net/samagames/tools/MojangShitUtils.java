package net.samagames.tools;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MojangShitUtils
{
    private static Class<?> itemClass;
    private static Class<?> itemsClass;
    private static Class<?> itemStackClass;
    private static Class<?> craftItemStackClass;
    private static Class<?> nbtTagCompoundClass;

    private static Method setStringMethod;
    private static Method setMethod;
    private static Method getStringMethod;
    private static Method hasKeyMethod;
    private static Method setTagMethod;
    private static Method hasTagMethod;
    private static Method getTagMethod;
    private static Method asBukkitCopyMethod;
    private static Method asNMSCopyMethod;
    private static Method getCompoundMethod;

    private static Field splashPotionField;
    private static Field lingeringPotionField;
    private static Field potionField;
    private static Field spawnEggField;

    public static ItemStack getPotion(String nmsPotionName, boolean splash, boolean lingering)
    {
        try
        {
            Object itemStack = itemStackClass.getDeclaredConstructor(itemClass, int.class).newInstance(splash ? splashPotionField.get(null) : lingering ? lingeringPotionField.get(null) : potionField.get(null), 1);

            Object tag = nbtTagCompoundClass.newInstance();
            setStringMethod.invoke(tag, "Potion", "minecraft:" + nmsPotionName);

            setTagMethod.invoke(itemStack, tag);

            return (ItemStack) asBukkitCopyMethod.invoke(null, itemStack);
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static ItemStack getMonsterEgg(EntityType entityType)
    {
        try
        {
            Object itemStack = itemStackClass.getDeclaredConstructor(itemClass, int.class).newInstance(spawnEggField.get(null), 1);

            Object entityTag = nbtTagCompoundClass.newInstance();
            setStringMethod.invoke(entityTag, "id", entityType.getName());

            Object tag = nbtTagCompoundClass.newInstance();
            setMethod.invoke(tag, "EntityTag", entityTag);

            setTagMethod.invoke(itemStack, tag);

            return (ItemStack) asBukkitCopyMethod.invoke(null, itemStack);
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getEntityByMonsterEgg(ItemStack bukkitEgg)
    {
        if (bukkitEgg.getType() != Material.MONSTER_EGG)
            return null;

        try
        {
            Object itemStack = asNMSCopyMethod.invoke(null, bukkitEgg);

            if (!((boolean) hasTagMethod.invoke(itemStack)))
                return null;

            Object tag = getTagMethod.invoke(itemStack);

            if (!((boolean) hasKeyMethod.invoke(tag, "EntityTag")))
                return null;

            Object entityTag = getCompoundMethod.invoke(tag, "EntityTag");

            if (!((boolean) hasKeyMethod.invoke(entityTag, "id")))
                return null;

            return (String) getStringMethod.invoke(entityTag, "id");
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    static
    {
        try
        {
            itemClass = Reflection.getNMSClass("Item");
            itemsClass = Reflection.getNMSClass("Items");
            itemStackClass = Reflection.getNMSClass("ItemStack");
            craftItemStackClass = Reflection.getOBCClass("inventory.CraftItemStack");
            nbtTagCompoundClass = Reflection.getNMSClass("NBTTagCompound");

            setStringMethod = nbtTagCompoundClass.getMethod("setString", String.class, String.class);
            setMethod = nbtTagCompoundClass.getMethod("set", String.class, nbtTagCompoundClass);
            getStringMethod = nbtTagCompoundClass.getMethod("getString", String.class);
            hasKeyMethod = nbtTagCompoundClass.getMethod("hasKey", String.class);
            setTagMethod = itemStackClass.getMethod("setTag", nbtTagCompoundClass);
            hasTagMethod = itemStackClass.getMethod("hasTag");
            getTagMethod = itemStackClass.getMethod("getTag");
            asBukkitCopyMethod = craftItemStackClass.getMethod("asBukkitCopy", itemStackClass);
            asNMSCopyMethod = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
            getCompoundMethod = nbtTagCompoundClass.getMethod("getCompound", String.class);

            splashPotionField = itemsClass.getField("SPLASH_POTION");
            lingeringPotionField = itemsClass.getField("LINGERING_POTION");
            potionField = itemsClass.getField("POTION");
            spawnEggField = itemsClass.getField("SPAWN_EGG");
        }
        catch (NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }
}
