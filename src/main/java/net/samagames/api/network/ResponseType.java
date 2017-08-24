package net.samagames.api.network;

import org.bukkit.ChatColor;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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
