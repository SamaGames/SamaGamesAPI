package net.samagames.tools;

import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerUtils
{
    public static String getFullyFormattedPlayerName(Player player)
    {
        return PermissionsBukkit.getPrefix(PermissionsBukkit.getApi().getUser(player.getUniqueId())) + PermissionsBukkit.getDisplay(PermissionsBukkit.getApi().getUser(player.getUniqueId())) + player.getName() + ChatColor.RESET;
    }
    
    public static String getColoredFormattedPlayerName(Player player)
    {
        return PermissionsBukkit.getPrefix(PermissionsBukkit.getApi().getUser(player.getUniqueId())) + player.getName() + ChatColor.RESET;
    }
}
