package net.samagames.core.listeners;

import net.samagames.api.channels.PacketsReceiver;
import net.samagames.core.APIPlugin;
import net.samagames.permissionsbukkit.PermissionsBukkit;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class GlobalChannelHandler implements PacketsReceiver {

	private final APIPlugin plugin;

	public GlobalChannelHandler(APIPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void receive(String channel, String packet) {
		if (packet.equalsIgnoreCase("reboot")) {
			plugin.getServer().shutdown();
		} else if (packet.equalsIgnoreCase("rebootIfEmpty")) {
			if (plugin.getServer().getOnlinePlayers().size() == 0)
				plugin.getServer().shutdown();
		} else if (packet.startsWith("moderator")) {
			String id = packet.split(" ")[1];
			UUID uuid = UUID.fromString(id);
			if (PermissionsBukkit.hasPermission(uuid, "games.modjoin"))
				plugin.getPlayerListener().moderatorsExpected.add(uuid);
		} else if (packet.startsWith("teleport")) {
			try  {
				String[] args = packet.split(" ");
				UUID uuid = UUID.fromString(args[1]);
				UUID target = UUID.fromString(args[2]);
				if (PermissionsBukkit.hasPermission(uuid, "games.modjoin")) {
					plugin.getPlayerListener().moderatorsExpected.add(uuid);
					plugin.getPlayerListener().teleportTargets.put(uuid, target);
				}
			} catch (Exception ignored) {
			}
		}
	}
}
