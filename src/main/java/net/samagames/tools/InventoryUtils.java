package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * Inventory utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class InventoryUtils
{
    /**
     * Clean player's inventory
     *
     * @param player Player
     */
    public static void cleanPlayer(Player player)
    {
        if(player != null && Bukkit.getPlayer(player.getUniqueId()) != null)
        {
            player.getInventory().clear();
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);

            player.setSaturation(20.0F);
            player.setHealth(player.getMaxHealth());
            player.setExp(0.0F);
            player.setLevel(0);

            for(PotionEffect potionEffect : player.getActivePotionEffects())
                player.removePotionEffect(potionEffect.getType());
        }
    }
}
