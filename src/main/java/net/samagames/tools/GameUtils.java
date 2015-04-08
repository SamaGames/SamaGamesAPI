package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameUtils
{
    public static void broadcastMessage(String message)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage(message);
        }
    }
    
    public static void broadcastSound(Sound s)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getPlayer().getPlayer().getLocation(), s, 1, 1);
        }
    }

    public static void broadcastSound(Sound s, int volume)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getPlayer().getPlayer().getLocation(), s, volume, 1);
        }
    }
    
    public static void broadcastSound(Sound s, Location l)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(l, s, 1, 1);
        }
    }
}
