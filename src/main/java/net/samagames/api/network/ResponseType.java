package net.samagames.api.network;

import org.bukkit.ChatColor;

/**
 * Response type enumeration
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public enum ResponseType
{
	ALLOW(),
	DENY_OTHER(),
	DENY_CANT_RECEIVE(ChatColor.RED + "La partie ne peut pas vous recevoir."),
	DENY_FULL(ChatColor.RED + "La partie est pleine."),
	DENY_VIPONLY(ChatColor.RED + "La partie est pleine. Devenez " + ChatColor.GREEN + "VIP" + ChatColor.RED + " pour rejoindre."),
	DENY_NOT_READY(ChatColor.RED + "Cette arène n'est pas prête. Merci de patienter."),
	DENY_IN_GAME(ChatColor.RED + "La partie est déjà en cours.");

	private String message = null;

	/**
	 * Empty constructor
	 */
	ResponseType() {}

	/**
	 * Constructor
	 *
	 * @param message Reason of disallowing a join request
     */
	ResponseType(String message) {
		this.message = message;
	}

	/**
	 * Get reason of disallowing a join request
	 *
	 * @return Reason
     */
	public String getMessage() {
		return message;
	}
}
