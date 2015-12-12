package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Game utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
