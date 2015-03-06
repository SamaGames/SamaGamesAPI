package net.samagames.samagamesapi.gameapi;

import net.samagames.samagamesapi.internal.APIPlugin;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

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

	public void send() {
		String encoded = new Gson().toJson(this);
		APIPlugin.getApi().getPubSub().send("gamesigns", encoded);
	}
}
