package net.samagames.api.games;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class GameSignData {

	protected String serverIdentification;
	protected String signZone;
	protected String mapLine;
	protected String stateLine;
	protected String slotsLine;
	protected boolean allowJoins;

	public GameSignData(String mapLine, String stateLine, String slotsLine, boolean allowJoins) {
		this.mapLine = mapLine;
		this.stateLine = stateLine;
		this.slotsLine = slotsLine;
		this.allowJoins = allowJoins;
	}

	public GameSignData(GameInfo info, String map, CommonStatuses status) {
		this.mapLine = map;
		this.stateLine = status.getDisplay();
		this.allowJoins = status.isCanJoin();
		this.slotsLine = "" + info.getConnectedPlayers() + ChatColor.DARK_GRAY + "/" + ChatColor.BLACK + info.getTotalMaxPlayers();
	}

	public GameSignData() {
	}

	public GameSignData(String serverIdentification, String signZone, String mapLine, String stateLine, String slotsLine, boolean allowJoins) {
		this.serverIdentification = serverIdentification;
		this.signZone = signZone;
		this.mapLine = mapLine;
		this.stateLine = stateLine;
		this.slotsLine = slotsLine;
		this.allowJoins = allowJoins;
	}

	public String getServerIdentification() {
		return serverIdentification;
	}

	public String getSignZone() {
		return signZone;
	}

	public String getMapLine() {
		return mapLine;
	}

	public void setMapLine(String mapLine) {
		this.mapLine = mapLine;
	}

	public String getStateLine() {
		return stateLine;
	}

	public void setStateLine(String stateLine) {
		this.stateLine = stateLine;
	}

	public String getSlotsLine() {
		return slotsLine;
	}

	public void setSlotsLine(String slotsLine) {
		this.slotsLine = slotsLine;
	}

	public boolean isAllowJoins() {
		return allowJoins;
	}

	public void setAllowJoins(boolean allowJoins) {
		this.allowJoins = allowJoins;
	}

	public void setServerIdentification(String serverIdentification) {
		this.serverIdentification = serverIdentification;
	}

	public void setSignZone(String signZone) {
		this.signZone = signZone;
	}

	public void display(Sign sign) {
		sign.setLine(0, Strings.join(serverIdentification.split("_"), " "));
		sign.setLine(1, mapLine);
		sign.setLine(2, slotsLine);
		sign.setLine(3, stateLine);
		sign.update();
	}

	public void getDebug(Player player) {
		player.sendMessage(new String[] {
				ChatColor.YELLOW + "-----[" + ChatColor.AQUA + " Signs Debug " + ChatColor.YELLOW + "]-----",
				ChatColor.YELLOW + "Server : " + ChatColor.AQUA + serverIdentification,
				ChatColor.YELLOW + "Sign Zone : " + ChatColor.AQUA + signZone,
				ChatColor.YELLOW + "State Line : " + ChatColor.AQUA + stateLine
		});
	}

}
