package net.samagames.api.permissions;

import net.samagames.permissionsapi.PermissionsAPI;
import net.samagames.permissionsapi.permissions.PermissionEntity;
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
public interface PermissionsManager {
	PermissionsAPI getApi();

	String getPrefix(PermissionEntity entity);

	String getSuffix(PermissionEntity entity);

	String getDisplay(PermissionEntity entity);

	boolean hasPermission(PermissionEntity entity, String permission);

	boolean hasPermission(UUID player, String permission);

	boolean hasPermission(Player player, String permission);

	boolean hasPermission(CommandSender sender, String permission);
}
