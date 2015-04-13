package net.samagames.core.api.resourcepacks;

import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.samagames.api.resourcepacks.ResourceCallback;
import net.samagames.api.resourcepacks.ResourcePacksManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ForcePackHandler implements ResourceCallback {

	private final ResourcePacksManagerImpl packsManager;

	public ForcePackHandler(ResourcePacksManagerImpl packsManager) {
		this.packsManager = packsManager;
	}

	@Override
	public void callback(Player player, String hash, PacketPlayInResourcePackStatus.EnumResourcePackStatus status) {
		if (status == PacketPlayInResourcePackStatus.EnumResourcePackStatus.SUCCESSFULLY_LOADED) {
			packsManager.forcePackSent.remove(player.getUniqueId());
		} else if (status == PacketPlayInResourcePackStatus.EnumResourcePackStatus.DECLINED || status == PacketPlayInResourcePackStatus.EnumResourcePackStatus.FAILED_DOWNLOAD) {
			player.kickPlayer(ChatColor.RED + "Il est nécessaire d'accepter le ressource pack pour jouer.");
			packsManager.forcePackSent.remove(player.getUniqueId());
		}
	}
}
