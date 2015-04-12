package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils
{
    public static String getFullyFormattedPlayerName(UUID uuid)
    {
        return PermissionsBukkit.getPrefix(PermissionsBukkit.getApi().getUser(uuid)) + PermissionsBukkit.getDisplay(PermissionsBukkit.getApi().getUser(uuid)) + SamaGamesAPI.get().getUUIDTranslator().getName(uuid) + ChatColor.RESET;
    }

    public static String getFullyFormattedPlayerName(Player player)
    {
        return getFullyFormattedPlayerName(player.getUniqueId());
    }
    
    public static String getColoredFormattedPlayerName(UUID uuid)
    {
        return PermissionsBukkit.getPrefix(PermissionsBukkit.getApi().getUser(uuid)) + SamaGamesAPI.get().getUUIDTranslator().getName(uuid) + ChatColor.RESET;
    }

    public static String getColoredFormattedPlayerName(Player player)
    {
        return getColoredFormattedPlayerName(player.getUniqueId());
    }
}
