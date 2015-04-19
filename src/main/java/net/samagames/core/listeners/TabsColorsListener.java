package net.samagames.core.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.core.APIPlugin;
import net.samagames.core.tabcolors.TeamManager;
import net.samagames.permissionsapi.permissions.PermissionUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class TabsColorsListener extends APIListener {

	protected final TeamManager manager;

	public TabsColorsListener(APIPlugin plugin) {
		super(plugin);

		manager = new TeamManager(plugin);
	}

	String replaceColors(String message) {
		String s = message;
		for (ChatColor color : ChatColor.values()) {
			s = s.replaceAll("(?i)&" + color.getChar(), "" + color);
		}
		return s;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player p = event.getPlayer();
		manager.playerJoin(p); // Passer ça en sync si crash //
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			if (plugin.isDatabaseEnabled()) {
				PermissionUser user = SamaGamesAPI.get().getPermissionsManager().getApi().getUser(p.getUniqueId());
				final String display = SamaGamesAPI.get().getPermissionsManager().getDisplay(user);
				String prefix = SamaGamesAPI.get().getPermissionsManager().getPrefix(user);

				final String displayn = replaceColors(display + "" + prefix) + p.getName();
				p.setDisplayName(displayn);
			} else {
				event.getPlayer().sendMessage(ChatColor.GOLD + "Base de données désactivée. Fonctionnalités limitées.");
			}
		});

		event.setJoinMessage("");
	}

	@EventHandler
	public void playerQuit(final PlayerQuitEvent event) {
		event.setQuitMessage("");
		manager.playerLeave(event.getPlayer());
		event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	@EventHandler
	public void playerKick(final PlayerKickEvent event)
	{
		event.setLeaveMessage("");
		manager.playerLeave(event.getPlayer());
		event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
