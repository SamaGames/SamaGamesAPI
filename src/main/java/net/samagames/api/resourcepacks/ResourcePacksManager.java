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
	 * Forces the use of the pack given in argument
	 * @param name The name of the pack you want to force the users to use
	 */
	public void forcePack(String name);

	/**
	 * Forces the use of the pack given in argument
	 * @param name The name of the pack you want to force the users to use
	 * @param callback a callback called when the download status changes
	 */
	public void forcePack(String name, ResourceCallback callback);
}
