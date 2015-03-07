package net.samagames.api.games;

import org.bukkit.ChatColor;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class JoinResponse {

	protected ResponseType responseType;
	protected String message;

	public JoinResponse(ResponseType responseType) {
		this.responseType = responseType;
	}

	public JoinResponse(ResponseType responseType, String message) {
		this.responseType = responseType;
		this.message = message;
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public enum ResponseType {
		ALLOW,
		DENY_OTHER,
		DENY_SERVER_FULL(ChatColor.RED + "Ce serveur est plein"),
		DENY_ALMOST_FULL(ChatColor.RED + "Ce serveur est plein. Devenez VIP pour accéder aux slots réservés."),
		DENY_INGAME(ChatColor.RED + "Le jeu est déjà en cours."),
		DENY_NOTREADY(ChatColor.RED + "Le serveur n'accepte pas les connexions actuellement.");

		protected String defMessage;
		ResponseType() {

		}

		ResponseType(String message) {
			this.defMessage = message;
		}

		public String getDefMessage() {
			return defMessage;
		}
	}

}
