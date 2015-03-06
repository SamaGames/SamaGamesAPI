package net.samagames.samagamesapi.api.names;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface UUIDTranslator {

	public UUID getUUID(String name, boolean allowMojangCheck);

	public default UUID getUUID(String name) {
		return getUUID(name, false);
	}

	public String getName(UUID uuid, boolean allowMojangCheck);

	public default String getName(UUID uuid) {
		return getName(uuid, false);
	}

}
