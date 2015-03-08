package net.samagames.api.games;

import org.bukkit.ChatColor;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public enum CommonStatuses {
	INGAME(ChatColor.DARK_RED + "- En cours -", false),
	AVAILABLE(ChatColor.GREEN + "» Jouer «", true),
	OFFLINE(ChatColor.DARK_RED + "- Hors ligne -", false),
	READY_TO_START(ChatColor.GOLD + "" + ChatColor.BOLD + "» Jouer «", true),
	REBOOT(ChatColor.DARK_RED + "- Reboot -", false),
	VIP(ChatColor.DARK_PURPLE + "► VIP ◄", true);

	private final boolean canJoin;
	private final String display;

	CommonStatuses(String display, boolean canJoin) {
		this.display = display;
		this.canJoin = canJoin;
	}

	public boolean isCanJoin() {
		return canJoin;
	}

	public String getDisplay() {
		return display;
	}
}
