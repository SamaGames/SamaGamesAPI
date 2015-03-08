package net.samagames.core.listeners;

import net.samagames.api.games.GameAPI;
import net.samagames.api.games.GameInfo;
import net.samagames.api.games.JoinResponse;
import net.samagames.core.APIPlugin;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015s
 * All rights reserved.
 */
public class PlayerListener extends APIListener {

	protected HashSet<UUID> moderatorsExpected = new HashSet<>();
	protected HashMap<UUID, UUID> teleportTargets = new HashMap<>();

	public PlayerListener(APIPlugin plugin) {
		super(plugin);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();

		if (moderatorsExpected.contains(player.getUniqueId())) // On traite après
			return;

		GameAPI api = this.api.getGameAPI();
		if (api.getGame() != null) {
			JoinResponse response = new JoinResponse(JoinResponse.ResponseType.ALLOW);
			GameInfo info = api.getGame().getGameInfo();
			if (info.getState() == GameInfo.GameState.INGAME) {
				response.setResponseType(JoinResponse.ResponseType.DENY_INGAME);
			} else if (info.getState() == GameInfo.GameState.NOT_READY) {
				response.setResponseType(JoinResponse.ResponseType.DENY_NOTREADY);
			} else if (info.getConnectedPlayers() > info.getTotalMaxPlayers() && !PermissionsBukkit.hasPermission(player, "games.joinfull")) {
				response.setResponseType(JoinResponse.ResponseType.DENY_SERVER_FULL);
			} else if (info.getConnectedPlayers() > info.getMaxPlayers() && !PermissionsBukkit.hasPermission(player, "games.joinvip")) {
				response.setResponseType(JoinResponse.ResponseType.DENY_ALMOST_FULL);
			}

			response = api.getGame().preJoinPlayer(player.getUniqueId(), response);
			if (response.getResponseType() != JoinResponse.ResponseType.ALLOW) {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + response.getMessage());
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
				event.setKickMessage(ChatColor.RED + response.getMessage());
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (moderatorsExpected.contains(player.getUniqueId())) {
			player.sendMessage(ChatColor.GOLD + "Vous avez rejoint cette arène en mode modération.");
			if (teleportTargets.containsKey(player.getUniqueId())) {
				UUID target = teleportTargets.get(player.getUniqueId());
				Player tar = Bukkit.getPlayer(target);
				if (tar != null)
					player.teleport(tar);
				teleportTargets.remove(player.getUniqueId());
			}

			GameAPI api = this.api.getGameAPI();
			if (api.getGame() != null) {
				api.getGame().joinModeration(player);
			}
			return;
		}

		GameAPI api = this.api.getGameAPI();
		if (api.getGame() != null) {
			JoinResponse response = new JoinResponse(JoinResponse.ResponseType.ALLOW);
			GameInfo info = api.getGame().getGameInfo();
			if (info.getState() == GameInfo.GameState.INGAME) {
				response.setResponseType(JoinResponse.ResponseType.DENY_INGAME);
			} else if (info.getState() == GameInfo.GameState.NOT_READY) {
				response.setResponseType(JoinResponse.ResponseType.DENY_NOTREADY);
			} else if (info.getConnectedPlayers() > info.getTotalMaxPlayers() && !PermissionsBukkit.hasPermission(player, "games.joinfull")) {
				response.setResponseType(JoinResponse.ResponseType.DENY_SERVER_FULL);
			} else if (info.getConnectedPlayers() > info.getMaxPlayers() && !PermissionsBukkit.hasPermission(player, "games.joinvip")) {
				response.setResponseType(JoinResponse.ResponseType.DENY_ALMOST_FULL);
			}

			response = api.getGame().preJoinPlayer(player.getUniqueId(), response);
			if (response.getResponseType() != JoinResponse.ResponseType.ALLOW) {
				player.kickPlayer(ChatColor.RED + response.getMessage());
			} else {
				String message = ChatColor.YELLOW + "${PSEUDO}" + ChatColor.YELLOW + " a rejoint la partie ! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "${JOUEURS}" + ChatColor.DARK_GRAY + "/" + ChatColor.RED + "${JOUEURS_MAX}" + ChatColor.DARK_GRAY + "]";
				message = message.replace("${PSEUDO}", player.getName());
				message = message.replace("${JOUEURS}", String.valueOf(info.getConnectedPlayers()));
				message = message.replace("${JOUEURS_MAX}", String.valueOf(info.getTotalMaxPlayers()));

				if(info.getConnectedPlayers() > info.getMaxPlayers()) {
					message += ChatColor.GREEN + " [Slots VIP]";
				}

				api.getGame().displayMessage(message);
			}
		}
	}

	@EventHandler
	public void onLogout(PlayerQuitEvent event) {
		if (moderatorsExpected.contains(event.getPlayer().getUniqueId())) {
			moderatorsExpected.remove(event.getPlayer().getUniqueId());
		}

		GameAPI api = this.api.getGameAPI();
		if (api.getGame() != null) {
			api.getGame().logout(event.getPlayer());
		}
	}

}
