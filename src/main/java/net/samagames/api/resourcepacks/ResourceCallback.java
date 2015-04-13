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
public interface ResourceCallback {

	/***
	 * Called when the download status is changed
	 * @param player THe player downloading the pack
	 * @param hash The resource pack hash
	 * @param status The download status
	 */
	public void callback(Player player, String hash, PacketPlayInResourcePackStatus.EnumResourcePackStatus status);
}
