package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Player utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class PlayerUtils
{
    /**
     * Get a fully formatted player name with
     * rank and color
     *
     * @param uuid Player's UUID
     *
     * @return Formatted name
     */
    public static String getFullyFormattedPlayerName(UUID uuid)
    {
        //TODO
        return "";
        //return SamaGamesAPI.get().getPermissionsManager().getPrefix(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(uuid)) + SamaGamesAPI.get().getPermissionsManager().getDisplay(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(uuid)) + SamaGamesAPI.get().getUUIDTranslator().getName(uuid, true) + ChatColor.RESET;
    }

    /**
     * Get a fully formatted player name with
     * rank and color
     *
     * @param player Player
     *
     * @return Formatted name
     */
    public static String getFullyFormattedPlayerName(Player player)
    {
        return getFullyFormattedPlayerName(player.getUniqueId());
    }

    /**
     * Get a colored formatted player name
     *
     * @param uuid Player's UUID
     *
     * @return Formatted name
     */
    public static String getColoredFormattedPlayerName(UUID uuid)
    {
        return "";
        //TODO
        //return SamaGamesAPI.get().getPermissionsManager().getPrefix(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(uuid)) + SamaGamesAPI.get().getUUIDTranslator().getName(uuid, true) + ChatColor.RESET;
    }

    /**
     * Get a colored formatted player name
     *
     * @param player Player
     *
     * @return Formatted name
     */
    public static String getColoredFormattedPlayerName(Player player)
    {
        return getColoredFormattedPlayerName(player.getUniqueId());
    }
}
