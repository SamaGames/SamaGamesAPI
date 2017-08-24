package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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
public class GameUtils
{
    /**
     * Broadcast a message to all players
     *
     * @param message Message
     */
    public static void broadcastMessage(String message)
    {
        for(Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(message);
    }

    /**
     * Broadcast a sound to all players
     *
     * @param sound Sound
     */
    public static void broadcastSound(Sound sound)
    {
        for(Player player : Bukkit.getOnlinePlayers())
            player.playSound(player.getPlayer().getPlayer().getLocation(), sound, 1, 1);
    }

    /**
     * Broadcast a sound to all players
     *
     * @param sound Sound
     * @param volume Sound's volume
     */
    public static void broadcastSound(Sound sound, int volume)
    {
        for(Player player : Bukkit.getOnlinePlayers())
            player.playSound(player.getPlayer().getPlayer().getLocation(), sound, volume, 1);
    }

    /**
     * Broadcast a sound to all players
     *
     * @param sound Sound
     * @param location Sound's location
     */
    public static void broadcastSound(Sound sound, Location location)
    {
        for(Player player : Bukkit.getOnlinePlayers())
            player.playSound(location, sound, 1, 1);
    }
}
