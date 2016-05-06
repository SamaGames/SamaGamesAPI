package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.permissions.IPermissionsEntity;
import net.samagames.api.player.AbstractPlayerData;
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
        AbstractPlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(uuid);
        IPermissionsEntity playerPermissionEntity = SamaGamesAPI.get().getPermissionsManager().getPlayer(uuid);

        return playerPermissionEntity.getDisplayPrefix() + playerPermissionEntity.getDisplayTag() + playerData.getDisplayName() + ChatColor.RESET;
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
        AbstractPlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(uuid);
        IPermissionsEntity playerPermissionEntity = SamaGamesAPI.get().getPermissionsManager().getPlayer(uuid);

        return playerPermissionEntity.getDisplayPrefix() + playerData.getDisplayName() + ChatColor.RESET;
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
