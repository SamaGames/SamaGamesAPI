package net.samagames.tools.powerups;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Power up object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public abstract class Powerup
{
    public abstract void onPickup(Player player);

    public abstract String getName();
    public abstract ItemStack getIcon();

    public abstract boolean isSpecial();

    public void spawn(Location location)
    {
        new ActivePowerup(this, location);
    }
}
