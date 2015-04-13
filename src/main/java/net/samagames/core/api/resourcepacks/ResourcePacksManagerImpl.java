package net.samagames.core.api.resourcepacks;

import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R2.PacketPlayOutResourcePackSend;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.resourcepacks.ResourceCallback;
import net.samagames.api.resourcepacks.ResourcePacksManager;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ResourcePacksManagerImpl implements ResourcePacksManager {

	private ProtocolHandler handler = null;
	private HashMap<UUID, ResourceCallback> callbackHashMap = new HashMap<>();

	@Override
	public void sendResourcePack(Player player, String url, String hash, ResourceCallback callback) {
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
	public void sendResourcePack(Player player, String url, String hash) {
		sendResourcePack(player, url, hash, null);
	}

	@Override
	public void resetResourcePack(Player player) {
		Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), () -> {
			Jedis jedis = SamaGamesAPI.get().getResource();
			String url = jedis.get("resourcepacks:reseturl");
			jedis.close();
			player.setResourcePack(url);
		});
	}
}
