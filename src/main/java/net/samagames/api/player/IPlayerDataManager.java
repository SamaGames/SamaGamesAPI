package net.samagames.api.player;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IPlayerDataManager {

	/**
	 * Retourne les données d'un joueur
	 * @param player UUID du joueur
	 * @return les données du joueur
	 */
	public PlayerData getPlayerData(UUID player);

	/**
	 * Retourne les données d'un joueur
	 * @param player	UUID du joueur
	 * @param forceRefresh	Forcer le rafraichissement depuis la base de données
	 * @return les données du joueur
	 */
	public PlayerData getPlayerData(UUID player, boolean forceRefresh);

	/**
	 * Décharge les données du joueur du cache local
	 * @param player uuid du joueur
	 */
	public void unload(UUID player);

}
