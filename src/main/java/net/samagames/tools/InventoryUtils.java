package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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
