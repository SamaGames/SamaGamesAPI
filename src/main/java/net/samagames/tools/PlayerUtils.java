package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils
{
    public static String getFullyFormattedPlayerName(UUID uuid)
    {
        return SamaGamesAPI.get().getPermissionsManager().getPrefix(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(uuid)) + SamaGamesAPI.get().getPermissionsManager().getDisplay(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(uuid)) + SamaGamesAPI.get().getUUIDTranslator().getName(uuid, true) + ChatColor.RESET;
    }

    public static String getFullyFormattedPlayerName(Player player)
    {
        return getFullyFormattedPlayerName(player.getUniqueId());
    }
    
    public static String getColoredFormattedPlayerName(UUID uuid)
    {
        return SamaGamesAPI.get().getPermissionsManager().getPrefix(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(uuid)) + SamaGamesAPI.get().getUUIDTranslator().getName(uuid, true) + ChatColor.RESET;
    }

    public static String getColoredFormattedPlayerName(Player player)
    {
        return getColoredFormattedPlayerName(player.getUniqueId());
    }
}
