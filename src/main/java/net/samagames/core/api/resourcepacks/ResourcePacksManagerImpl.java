package net.samagames.core.api.resourcepacks;

import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R2.PacketPlayOutResourcePackSend;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.resourcepacks.ResourceCallback;
import net.samagames.api.resourcepacks.ResourcePacksManager;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ResourcePacksManagerImpl implements ResourcePacksManager, Listener {

	private ProtocolHandler handler = null;
	private HashMap<UUID, ResourceCallback> callbackHashMap = new HashMap<>();

	protected HashSet<UUID> forcePackSent = new HashSet<>();
	private String forceUrl;
	private String forceHash;

	@Override
	public void forcePack(String name) {
		Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), () -> {
			Jedis jedis = SamaGamesAPI.get().getResource();
			forceUrl = jedis.hget("resourcepack:" + name, "url");
			forceHash = jedis.hget("resourcepack:" + name, "hash");
			jedis.close();
		});
	}

	public ResourcePacksManagerImpl() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());
	}

	@Override
	public void sendResourcePack(Player player, String name, ResourceCallback callback) {
		Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), () -> {
			Jedis jedis = SamaGamesAPI.get().getResource();
			String url = jedis.hget("resourcepack:" + name, "url");
			String hash = jedis.hget("resourcepack:" + name, "hash");
			jedis.close();

			sendPack(player, url, hash, callback);
		});
	}

	void sendPack(Player player, String url, String hash, ResourceCallback callback) {
		if (handler == null) {
			handler = new ProtocolHandler(APIPlugin.getInstance(), this);
		}

		if (callback != null)
			callbackHashMap.put(player.getUniqueId(), callback);

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutResourcePackSend(url, hash));
	}

	void handle(Player player, String hash, PacketPlayInResourcePackStatus.EnumResourcePackStatus state) {
		ResourceCallback callback = callbackHashMap.get(player.getUniqueId());
		if (callback != null)
			callback.callback(player, hash, state);

		if (state != PacketPlayInResourcePackStatus.EnumResourcePackStatus.ACCEPTED)
			callbackHashMap.remove(player.getUniqueId());
	}

	@Override
	public void sendResourcePack(Player player, String name) {
		sendResourcePack(player, name, null);
	}

	@Override
	public void resetResourcePack(Player player) {
		Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), () -> {
			Jedis jedis = SamaGamesAPI.get().getResource();
			String url = jedis.get("resourcepacks:reseturl");
			jedis.close();
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutResourcePackSend(url, "reset.zip"));
		});
	}

	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		resetResourcePack(event.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (forceHash != null && forceUrl != null) {
			forcePackSent.add(event.getPlayer().getUniqueId());
			sendPack(event.getPlayer(), forceHash, forceUrl, new ForcePackHandler(this));
			Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> {
				if (forcePackSent.contains(event.getPlayer().getUniqueId())) {
					event.getPlayer().kickPlayer(ChatColor.RED + "Il est nécessaire d'accepter le pack pour jouer à ce jeu.");
				}
				forcePackSent.remove(event.getPlayer().getUniqueId());
			}, 400);
		}
	}
}
