package net.samagames.core.listeners;

import net.samagames.core.APIPlugin;
import net.samagames.permissionsapi.permissions.PermissionUser;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ChatFormatter extends APIListener {
	public ChatFormatter(APIPlugin plugin) {
		super(plugin);
	}

	String replaceColors(String message) {
		String s = message;
		for (ChatColor color : ChatColor.values()) {
			s = s.replaceAll("(?i)&" + color.getChar(), "" + color);
		}
		return s;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		PermissionUser user = PermissionsBukkit.getApi().getUser(p.getUniqueId());
		String format = "<display><prefix><name><suffix>: ";

		String display = replaceColors(PermissionsBukkit.getDisplay(user));
		String prefix = replaceColors(PermissionsBukkit.getPrefix(user));
		String suffix = replaceColors(PermissionsBukkit.getSuffix(user));

		String tmp = format;
		tmp = tmp.replaceAll("<display>", "" + display + ChatColor.WHITE);
		tmp = tmp.replaceAll("<prefix>", "" + prefix);
		tmp = tmp.replaceAll("<name>", "" + p.getName());
		tmp = tmp.replaceAll("<suffix>", "" + suffix);

		if (p.hasPermission("bungeefilter.bypass")) {
			tmp += replaceColors(event.getMessage());
		} else {
			tmp += event.getMessage().replaceAll("&r", "");
		}

		event.setFormat(tmp.replace("%", "%%"));
	}
}
