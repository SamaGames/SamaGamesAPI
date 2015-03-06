package net.samagames.samagamesapi.internal.api.player;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class TimedPlayerDat {

	protected Date loadedAt;
	protected Map<String, String> data;
	protected UUID playerId;

	public TimedPlayerDat(Date loadedAt, Map<String, String> data, UUID playerId) {
		this.loadedAt = loadedAt;
		this.data = data;
		this.playerId = playerId;
	}

	public Date getLoadedAt() {
		return loadedAt;
	}

	public Map<String, String> getData() {
		return data;
	}

	public UUID getPlayerId() {
		return playerId;
	}
}
