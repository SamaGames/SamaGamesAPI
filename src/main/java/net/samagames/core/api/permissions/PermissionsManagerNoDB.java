package net.samagames.core.api.permissions;

import net.samagames.permissionsapi.PermissionsAPI;
import net.samagames.permissionsapi.permissions.PermissionEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PermissionsManagerNoDB extends BasicPermissionManager {

	@Override
	public String getPrefix(PermissionEntity entity) {
		return ChatColor.RED + "";
	}

	@Override
	public String getSuffix(PermissionEntity entity) {
		return ChatColor.WHITE + "";
	}

	@Override
	public String getDisplay(PermissionEntity entity) {
		return ChatColor.RED + "[ADM/NODB] ";
	}

	@Override
	public boolean hasPermission(PermissionEntity entity, String permission) {
		logWarning("Granting permission as database is disabled.");
		return true;
	}

	/**
	 * Only works for onlineplayers.
	 * @param player UUID for the player. Must be online
	 * @param permission The permission to check
	 * @return
	 */
	@Override
	public boolean hasPermission(UUID player, String permission) {
		PermissionEntity entity = api.getManager().getUserFromCache(player);
		if (entity == null) {
			logWarning("Entity "+player+" is not found in cache.");
			return false;
		}
		return entity.hasPermission(permission);
	}


}
