package net.samagames.api.resourcepacks;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

/**
 * Resource packs callback class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IResourceCallback
{
	/***
	 * Called when the download status is changed
     *
	 * @param player THe player downloading the pack
	 * @param status The download status
	 */
    void callback(Player player, PlayerResourcePackStatusEvent.Status status);

	/**
	 * Called when a player is about to be automatically kicked because he doesn't have the pack
     *
	 * @param player The player to be kicked
     *
	 * @return {@code true} to kick the player, false to cancel
	 */
    boolean automaticKick(Player player);
}
