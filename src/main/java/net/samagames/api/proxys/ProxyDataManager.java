package net.samagames.api.proxys;

import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ProxyDataManager {

	/**
	 * Renvoie les données RedisBungee du joueur
	 * @param player UUID du joueur à récupérer
	 * @return Une map Clé - Valeur des données stockées
 	 */
	public Map<String, String> getPlayerData(UUID player);

	/**
	 * Retourne le serveur actuel d'un joueur
	 * @param player UUID du joueur
	 * @return le nom du serveur actuel du joueur ou <code>null</code> si le joueur est déconnecté
	 */
	public String getPlayerServer(UUID player);

	/**
	 * Retourne le proxy actuel d'un joueur
	 * @param player UUID du joueur
	 * @return le nom du proxy actuel du joueur ou <code>null</code> si le joueur est déconnecté
	 */
	public String getPlayerProxy(UUID player);

	/**
	 * Retourne l'ip actuelle d'un joueur
	 * @param player UUID du joueur
	 * @return l'ip du joueur ou <code>null</code> si le joueur est déconnecté
	 */
	public String getPlayerIP(UUID player);

}
