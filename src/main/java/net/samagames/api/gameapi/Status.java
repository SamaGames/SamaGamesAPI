package net.samagames.api.gameapi;

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

	STARTING("starting", ChatColor.DARK_RED + "≈ Reboot ≈", false),
	WAITING_FOR_PLAYERS("waitingForPlayers", ChatColor.GREEN + "» Jouer «", true),
	READY_TO_START("readyToStart", ChatColor.GOLD + "" + ChatColor.BOLD + "» Jouer «", true),
	IN_GAME("inGame", ChatColor.DARK_RED + "- En cours -", false),
	FINISHED("finished", ChatColor.DARK_RED + "≈ Reboot ≈", false),
	REBOOTING("rebooting", ChatColor.DARK_RED + "≈ Reboot ≈", false),
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
