package net.samagames.api.resourcepacks;

import org.bukkit.entity.Player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ResourcePacksManager {

	/**
	 * Request the user to download a pack
	 * @param player player receiving the pack
	 * @param url pack download url
	 * @param hash pack sha1 hash
	 * @param callback callback
	 */
	public void sendResourcePack(Player player, String url, String hash, ResourceCallback callback);

	public void sendResourcePack(Player player, String url, String hash);

}
