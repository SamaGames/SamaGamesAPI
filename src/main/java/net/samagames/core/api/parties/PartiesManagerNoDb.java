package net.samagames.core.api.parties;

import net.samagames.api.parties.PartiesManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PartiesManagerNoDb implements PartiesManager {
	@Override
	public UUID getPlayerParty(UUID player) {
		return null;
	}

	@Override
	public HashMap<UUID, String> getPlayersInParty(UUID party) {
		return null;
	}

	@Override
	public String getCurrentServer(UUID party) {
		return null;
	}

	@Override
	public UUID getLeader(UUID party) {
		return null;
	}
}
