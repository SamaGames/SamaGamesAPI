package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.permissions.IPermissionsEntity;
import net.samagames.api.player.AbstractPlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

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
