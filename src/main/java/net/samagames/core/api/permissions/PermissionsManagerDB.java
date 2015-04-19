package net.samagames.core.api.permissions;

import net.samagames.api.SamaGamesAPI;
import net.samagames.permissionsapi.PermissionsAPI;
import net.samagames.permissionsapi.permissions.PermissionEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PermissionsManagerDB extends BasicPermissionManager {

	@Override
	public String getPrefix(PermissionEntity entity) {
		String prefix = entity.getProperty("prefix");
		if (prefix == null)
			return null;
		prefix = prefix.replaceAll("&s", " ");
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		return prefix;
	}

	@Override
	public String getSuffix(PermissionEntity entity) {
		String suffix = entity.getProperty("suffix");
		if (suffix == null)
			return null;
		suffix = suffix.replaceAll("&s", " ");
		suffix = ChatColor.translateAlternateColorCodes('&', suffix);
		return suffix;
	}

	@Override
	public String getDisplay(PermissionEntity entity) {
		String display = entity.getProperty("display");
		if (display == null)
			return null;
		display = display.replaceAll("&s", " ");
		display = ChatColor.translateAlternateColorCodes('&', display);
		return display;
	}

	@Override
	public boolean hasPermission(PermissionEntity entity, String permission) {
		return entity.hasPermission(permission);
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
			Bukkit.getLogger().warning("Entity "+player+" is not found in cache.");
			return false;
		}
		return entity.hasPermission(permission);
	}


}
