package net.samagames.api.permissions;

import net.samagames.api.permissions.permissions.PermissionEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IPermissionsManager {

	/**
	 * Returns PermissionsAPI for low level permissions operations
	 * @return PermissionsAPI the API
	 */
	PermissionsAPI getApi();

	/**
	 * Returns the prefix for an entity (player or group). The prefix is the color of the name.
	 * @param entity The entity you wan't to get the prefix for
	 * @return A ready-to-display prefix
	 */
	String getPrefix(PermissionEntity entity);

	/**
	 * Returns the suffix for an entity (player or group). The suffix is applied at the end of the name.
	 * @param entity The entity you wan't to get the suffix for
	 * @return A ready-to-display suffix
	 */
	String getSuffix(PermissionEntity entity);

	/**
	 * Returns the display tag for an entity (player or group).
	 * @param entity The entity you wan't to get the display tag for
	 * @return A ready-to-display display tag
	 */
	String getDisplay(PermissionEntity entity);

	/**
	 * Checks if an entity has a permission
	 * @param entity The entity you want to check
	 * @param permission The permission you wan't to check
	 * @return true if the entity has the permission
	 */
	boolean hasPermission(PermissionEntity entity, String permission);

	/**
	 * Checks if a player has a permission
	 * @param player The player you want to check
	 * @param permission The permission you wan't to check
	 * @return true if the entity has the permission
	 */
	boolean hasPermission(UUID player, String permission);

	/**
	 * Checks if a player has a permission
	 * @param player The player you want to check
	 * @param permission The permission you wan't to check
	 * @return true if the entity has the permission
	 */
	boolean hasPermission(Player player, String permission);

	/**
	 * Checks if a sender has a permission
	 * @param sender The sender you want to check
	 * @param permission The permission you wan't to check
	 * @return true if the entity has the permission
	 */
	boolean hasPermission(CommandSender sender, String permission);
}
