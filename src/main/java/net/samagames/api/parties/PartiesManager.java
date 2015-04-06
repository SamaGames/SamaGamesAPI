package net.samagames.api.parties;

import java.util.HashMap;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface PartiesManager {
	UUID getPlayerParty(UUID player);

	HashMap<UUID, String> getPlayersInParty(UUID party);

	String getCurrentServer(UUID party);

	UUID getLeader(UUID party);
}
