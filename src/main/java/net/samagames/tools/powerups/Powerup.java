package net.samagames.tools.powerups;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Power up interface
 *
 * Copyright (c) for SamaGames
 * All right reserved
 *
 * @author Amaury Carrade
 */
public interface Powerup
{
    void onPickup(Player player);

    String getName();
    ItemStack getIcon();

    double getWeight();

    boolean isSpecial();

    default ActivePowerup spawn(Location location)
    {
        return new ActivePowerup(this, location);
    }
}
