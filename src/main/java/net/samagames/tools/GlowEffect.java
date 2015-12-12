package net.samagames.tools;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;


/**
 * A fake enchantment to add a glowing effect on any item.
 *
 * Copyright (c) for SamaGames
 * All right reserved
 *
 * @author Amaury Carrade
 */
public class GlowEffect extends EnchantmentWrapper
{
    private static Enchantment glow;
    private final static int ENCHANTMENT_ID = 254;
    private final static String ENCHANTMENT_NAME = "GlowEffect";

    public GlowEffect(int id)
    {
        super(id);
    }

    @Override
    public boolean canEnchantItem(ItemStack item)
    {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment other)
    {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget()
    {
        return null;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public String getName()
    {
        return ENCHANTMENT_NAME;
    }

    @Override
    public int getStartLevel()
    {
        return 1;
    }

    /**
     * Get glow enchantment
     *
     * @return Glow enchantment
     */
    public static Enchantment getGlow()
    {
        if (glow != null)
        {
            return glow;
        }

        try
        {
            // We change this to force Bukkit to accept a new enchantment.
            // Thanks to Cybermaxke on BukkitDev.
            Field acceptingNewField = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNewField.setAccessible(true);
            acceptingNewField.set(null, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            glow = new GlowEffect(ENCHANTMENT_ID);
            Enchantment.registerEnchantment(glow);
        }
        catch(IllegalArgumentException e)
        {
            // If the enchantment is already registered - happens on server reload
            glow = Enchantment.getById(ENCHANTMENT_ID); // getByID required - by name it doesn't work (returns null).
        }

        return glow;
    }

    /**
     * Add glow effect to an ItemStack {@link ItemStack}
     *
     * @param item Object
     *
     * @return Object with glow effect
     */
    public static ItemStack addGlow(ItemStack item)
    {
        if(item == null)
            return null;

        Enchantment glow = getGlow();

        if(glow != null)
            item.addEnchantment(glow, 1);

        return item;
    }
}
