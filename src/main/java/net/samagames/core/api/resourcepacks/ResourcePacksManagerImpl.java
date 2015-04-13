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
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ResourcePacksManagerImpl implements ResourcePacksManager, Listener {

	private final String resetUrl;
	private ProtocolHandler handler = null;

	protected HashSet<UUID> forcePackSent = new HashSet<>();
	private String forceUrl;
	private String forceHash;
	private ResourceCallback callback;

	public ResourcePacksManagerImpl() {
		Bukkit.getPluginManager().registerEvents(this, APIPlugin.getInstance());

		Jedis jedis = SamaGamesAPI.get().getResource();
		this.resetUrl = jedis.get("resourcepacks:reseturl");
		APIPlugin.getInstance().getLogger().info("Resource packs reset URL defined to " + resetUrl);
		jedis.close();
	}

	@Override
	public void forcePack(String name) {
		forcePack(name, null);
	}

	@Override
	public void forcePack(String name, ResourceCallback callback) {
		Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), () -> {
			Jedis jedis = SamaGamesAPI.get().getResource();
			forceUrl = jedis.hget("resourcepack:" + name, "url");
			forceHash = jedis.hget("resourcepack:" + name, "hash");
			jedis.close();

			APIPlugin.getInstance().getLogger().info("Defined automatic resource pack : " + forceUrl + " with hash " + forceHash);
		});

		this.callback = callback;
	}

	void sendPack(Player player, String url, String hash) {
		APIPlugin.getInstance().getLogger().info("Sending pack to " + player.getName() + " : " + url + " with hash " + hash);

		if (handler == null) {
			handler = new ProtocolHandler(APIPlugin.getInstance(), this);
		}

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutResourcePackSend(url, hash));
	}

	void handle(Player player, PacketPlayInResourcePackStatus.EnumResourcePackStatus state) {
		if (callback != null)
			callback.callback(player, state);

		if (state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.SUCCESSFULLY_LOADED) {
			forcePackSent.remove(player.getUniqueId());
			Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), () -> {
				Jedis jedis = SamaGamesAPI.get().getResource();
				jedis.sadd("playersWithPack", player.getUniqueId().toString());
				jedis.close();
			});
		} else if (state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.DECLINED || state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.FAILED_DOWNLOAD) {
			forcePackSent.remove(player.getUniqueId());
			if (callback == null || callback.automaticKick(player))
				player.kickPlayer(ChatColor.RED + "Il est nécessaire d'accepter le ressource pack pour jouer.");
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (forceHash != null && forceUrl != null) {
			Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> {
				forcePackSent.add(player.getUniqueId());
				sendPack(player, forceUrl, forceHash);

				Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> {
					if (forcePackSent.contains(player.getUniqueId())) {
						forcePackSent.remove(player.getUniqueId());
						if (callback == null || callback.automaticKick(player))
							player.kickPlayer(ChatColor.RED + "Il est nécessaire d'accepter le ressource pack pour jouer.");
					}
					forcePackSent.remove(player.getUniqueId());
				}, 400);
			}, 100);
		} else {
			Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> {
				Jedis jedis = SamaGamesAPI.get().getResource();
				Long l = jedis.srem("playersWithPack");
				jedis.close();

				if (l > 0)
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutResourcePackSend(resetUrl, "samareset.zip"));
			}, 100);
		}
	}
}
