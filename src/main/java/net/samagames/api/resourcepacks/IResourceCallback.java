package net.samagames.api.resourcepacks;

import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import org.bukkit.entity.Player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IResourceCallback {

	/***
	 * Called when the download status is changed
	 * @param player THe player downloading the pack
	 * @param status The download status
	 */
	public void callback(Player player, PacketPlayInResourcePackStatus.EnumResourcePackStatus status);

	/**
	 * Called when a player is about to be automatically kicked because he doesn't have the pack
	 * @param player The player to be kicked
	 * @return true to kick the player, false to cancel
	 */
	public boolean automaticKick(Player player);
}
