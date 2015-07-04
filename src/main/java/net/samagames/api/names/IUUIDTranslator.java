package net.samagames.api.names;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IUUIDTranslator {

	/**
	 * Renvoie l'UUID d'un joueur en fonction du pseudo
	 * @param name 				Pseudo du joueur recherché
	 * @param allowMojangCheck  Autoriser les requêtes vers l'API Mojang
	 * @return l'uuid du joueur demandé
	 */
	public UUID getUUID(String name, boolean allowMojangCheck);

	public default UUID getUUID(String name) {
		return getUUID(name, false);
	}

	/**
	 * Renvoie le nom d'un joueur en fonction de son UUID
	 * @param uuid 				UUID du joueur recherché
	 * @param allowMojangCheck  Autoriser les requêtes vers l'API Mojang
	 * @return le pseudo du joueur demandé
	 */
	public String getName(UUID uuid, boolean allowMojangCheck);

	public default String getName(UUID uuid) {
		return getName(uuid, false);
	}

}
