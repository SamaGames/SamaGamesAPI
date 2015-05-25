package net.samagames.core.api.resourcepacks;

import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.samagames.api.resourcepacks.ResourceCallback;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class KillerTask extends BukkitRunnable {

	private final Player player;
	private final ResourceCallback callback;
	private final ResourcePacksManagerImpl impl;
	private int remaining;

	public KillerTask(Player player, ResourceCallback callback, ResourcePacksManagerImpl impl) {
		this.player = player;
		this.callback = callback;
		this.impl = impl;
	}

	/**
	 *
	 * @param state
	 * @return true if the  task can be removed
	 */
	public void changeState(PacketPlayInResourcePackStatus.EnumResourcePackStatus state) {
		if (state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.ACCEPTED) {
			remaining = 60;
		} else if (state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.SUCCESSFULLY_LOADED) {
			this.cancel();
			impl.removeKillerFor(player.getUniqueId());
		} else if (state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.DECLINED || state == PacketPlayInResourcePackStatus.EnumResourcePackStatus.FAILED_DOWNLOAD) {
			this.cancel();
			if (callback == null || callback.automaticKick(player))
				player.kickPlayer(ChatColor.RED + "Il est nécessaire d'accepter le ressource pack pour jouer.");

			impl.removeKillerFor(player.getUniqueId());
		}
	}

	@Override
	public void run() {
		remaining --;
		if (remaining <= 0) {
			if (callback == null || callback.automaticKick(player))
				player.kickPlayer(ChatColor.RED + "Il est nécessaire d'accepter le ressource pack pour jouer.");

			impl.removeKillerFor(player.getUniqueId());
		}
	}
}
