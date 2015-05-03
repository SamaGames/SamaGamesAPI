package net.samagames.api.games;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameUtils
{
    /**
     * Broadcast a sound to all players
     *
     * @param sound The sound
     */
    public static void broadcastSound(Sound sound)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

    /**
     * Broadcast a sound to all player with a given volume
     *
     * @param sound The sound
     * @param volume The volume
     */
    public static void broadcastSound(Sound sound, int volume)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getLocation(), sound, volume, 1);
        }
    }

    /**
     * Broadcast a sound to all player to a specific location
     *
     * @param sound The sound
     * @param location The location
     */
    public static void broadcastSound(Sound sound, Location location)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(location, sound, 1, 1);
        }
    }
}
