package net.samagames.api.games;

import org.bukkit.ChatColor;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 *
 * This enum is provided as it. You can create your own status enum, if you think this one doesn't fit to your needs.
 *
 */
public enum Status implements StatusEnum {

	/**
	 * The server is booting and is not ready to host players
	 */
	STARTING("starting", ChatColor.DARK_RED + "≈ Reboot ≈", false),

	/**
	 * The server is just waiting for players to start
	 */
	WAITING_FOR_PLAYERS("waitingForPlayers", ChatColor.GREEN + "» Jouer «", true),

	/**
	 * The server has enough players to start
	 */
	READY_TO_START("readyToStart", ChatColor.GOLD + "" + ChatColor.BOLD + "» Jouer «", true),

	/**
	 * The game is started, players cannot join anymore
	 */
	IN_GAME("inGame", ChatColor.DARK_RED + "- En cours -", false),

	/**
	 * The game is finished, it will reboot soon
	 */
	FINISHED("finished", ChatColor.DARK_RED + "≈ Reboot ≈", false),

	/**
	 * The server is rebooting
	 */
	REBOOTING("rebooting", ChatColor.DARK_RED + "≈ Reboot ≈", false),

	/**
	 * The server isn't responding
	 */
	NOT_RESPONDING("idle", ChatColor.DARK_RED + "Hors Service", false);

	private final String display;
	private final String id;
	private final boolean allowJoin;

	private Status(String id, String display, boolean allowJoin) {
		this.id = id;
		this.display = display;
		this.allowJoin = allowJoin;
	}

	public boolean isAllowJoin() {
		return allowJoin;
	}

	public String getDisplay() {
		return display;
	}

	public String getId() {
		return id;
	}

	public static Status fromString(String str) {
		for (Status status : Status.values())
			if (status.getId().equals(str))
				return status;

		return null;
	}

	@Override
	public String toString() {
		return getId();
	}
}
