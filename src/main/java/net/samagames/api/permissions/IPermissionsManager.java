package net.samagames.api.permissions;

import org.bukkit.command.CommandSender;
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
public interface IPermissionsManager
{

	/**
	 * Get the permission player with all data
	 * @param player UUID of the player
	 * @return
     */
	IPermissionsEntity getPlayer(UUID player);

	/**
	 * Returns the prefix for an entity (player or group). The prefix is the color of the name.
     *
	 * @param entity The entity you wan't to get the prefix for
     *
	 * @return A ready-to-display prefix
	 */
	String getPrefix(IPermissionsEntity entity);

	/**
	 * Returns the suffix for an entity (player or group). The suffix is applied at the end of the name.
     *
	 * @param entity The entity you wan't to get the suffix for
     *
	 * @return A ready-to-display suffix
	 */
	String getSuffix(IPermissionsEntity entity);

	/**
	 * Returns the display tag for an entity (player or group).
     *
	 * @param entity The entity you wan't to get the display tag for
     *
	 * @return A ready-to-display display tag
	 */
	String getDisplay(IPermissionsEntity entity);

	/**
	 * Checks if an entity has a permission
     *
	 * @param entity The entity you want to check
	 * @param permission The permission you wan't to check
     *
	 * @return {@code true} if the entity has the permission
	 */
	boolean hasPermission(IPermissionsEntity entity, String permission);

	/**
	 * Checks if a player has a permission
     *
	 * @param player The player you want to check
	 * @param permission The permission you wan't to check
     *
	 * @return {@code true} if the entity has the permission
	 */
	boolean hasPermission(UUID player, String permission);

	/**
	 * Checks if a player has a permission
     *
	 * @param player The player you want to check
	 * @param permission The permission you wan't to check
     *
	 * @return {@code true} if the entity has the permission
	 */
	boolean hasPermission(Player player, String permission);

	/**
	 * Checks if a sender has a permission
     *
	 * @param sender The sender you want to check
	 * @param permission The permission you wan't to check
     *
	 * @return {@code true} if the entity has the permission
	 */
	boolean hasPermission(CommandSender sender, String permission);
}
