package net.samagames.api.player;

import java.util.*;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class PlayerData {

	protected Map<String, String> playerData = new HashMap<>();
	protected Date lastRefresh;
	protected final UUID playerID;

	protected PlayerData(UUID playerID) {
		this.playerID = playerID;
	}

	/**
	 * Permet d'obtenir l'UUID du joueur
	 * @return UUID du joueur
	 */
	public UUID getPlayerID() {
		return playerID;
	}

	/**
	 * Renvoie la dernière date d'actualisation depuis la base de données
	 * @return Dernière actualisation
	 */
	public Date getLastRefresh() {
		return lastRefresh;
	}

	/**
	 * Obtient les clés des données stockées
	 * @return Liste des clés stockées
	 */
	public Set<String> getKeys() {
		return playerData.keySet();
	}

	/**
	 * Obtient l'ensemble des données du joueur
	 * @return données du joueur
	 */
	public Map<String, String> getValues() {
		return playerData;
	}

	/**
	 * Permet de savoir si les données du joueur contiennent une clé en particulier
	 * @param key Clé à tester
	 * @return true si cette clé existe
	 */
	public boolean contains(String key) {
		return playerData.containsKey(key);
	}

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @return valeur de la clé, null si elle n'existe pas
	 */
	public String get(String key) {
		return playerData.get(key);
	}

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @param def Valeur par défaut
	 * @return valeur de la clé, <code>def</code> si elle n'existe pas
	 */
	public String get(String key, String def) {
		return (contains(key) ? get(key) : def);
	}

	/**
	 * Définit une valeur dans les données du joueur
	 * @param key	Clé à définir
	 * @param value Valeur à définir
	 */
	public abstract void set(String key, String value);

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @return valeur de la clé, null si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Integer getInt(String key) {
		String val = get(key);
		if (val == null)
			return null;

		try {
			return Integer.valueOf(val);
		} catch (Exception e) {
			throw new InvalidTypeException("This value is not an int.");
		}
	}

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @param def Valeur par défaut
	 * @return valeur de la clé, <code>def</code> si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Integer getInt(String key, int def) {
		Integer ret = getInt(key);
		if (ret == null)
			return def;
		else
			return ret;
	}

	/**
	 * Définit une valeur dans les données du joueur
	 * @param key	Clé à définir
	 * @param value Valeur à définir
	 */
	public abstract void setInt(String key, int value);

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @return valeur de la clé, null si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Boolean getBoolean(String key) {
		String val = get(key);
		if (val == null)
			return null;

		try {
			return Boolean.valueOf(val);
		} catch (Exception e) {
			throw new InvalidTypeException("This value is not a boolean.");
		}
	}

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @param def Valeur par défaut
	 * @return valeur de la clé, <code>def</code> si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Boolean getBoolean(String key, boolean def) {
		Boolean ret = getBoolean(key);
		if (ret == null)
			return def;
		else
			return ret;
	}

	/**
	 * Définit une valeur dans les données du joueur
	 * @param key	Clé à définir
	 * @param value Valeur à définir
	 */
	public abstract void setBoolean(String key, boolean value);

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @return valeur de la clé, null si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Double getDouble(String key) {
		String val = get(key);
		if (val == null)
			return null;

		try {
			return Double.valueOf(val);
		} catch (Exception e) {
			throw new InvalidTypeException("This value is not a double.");
		}
	}

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @param def Valeur par défaut
	 * @return valeur de la clé, <code>def</code> si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Double getDouble(String key, double def) {
		Double ret = getDouble(key);
		if (ret == null)
			return def;
		else
			return ret;
	}

	/**
	 * Définit une valeur dans les données du joueur
	 * @param key	Clé à définir
	 * @param value Valeur à définir
	 */
	public abstract void setDouble(String key, double value);

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @return valeur de la clé, null si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Long getLong(String key) {
		String val = get(key);
		if (val == null)
			return null;

		try {
			return Long.valueOf(val);
		} catch (Exception e) {
			throw new InvalidTypeException("This value is not a long.");
		}
	}

	/**
	 * Récupère la valeur d'une clé
	 * @param key clé à récupérer
	 * @param def Valeur par défaut
	 * @return valeur de la clé, <code>def</code> si elle n'existe pas
	 * @throws net.samagames.api.player.InvalidTypeException si la valeur n'est pas du bon type
	 */
	public Long getLong(String key, long def) {
		Long ret = getLong(key);
		if (ret == null)
			return def;
		else
			return ret;
	}

	/**
	 * Définit une valeur dans les données du joueur
	 * @param key	Clé à définir
	 * @param value Valeur à définir
	 */
	public abstract void setLong(String key, long value);

	/*
	Coins management
	*/

	/**
	 * Retourne le nombre de coins du joueur
	 * ATTENTION ! Ce nombre de coins date du dernier refresh, obtenable via <code>getLastRefresh()</code>
	 * @return le nombre de coins du joueur
	 */
	public long getCoins() {
		return getLong("coins", 0L);
	}

	/**
	 * Augmente le nombre de coins du joueur. Ne tient compte d'aucun multiplicateur et n'affiche aucun message.
	 * @param incrBy Montant à créditer
	 * @return le nouveau nombre de coins du joueur
	 */
	public abstract long increaseCoins(long incrBy);

	/**
	 * Diminue le nombre de coins du joueur. Ne tient compte d'aucun multiplicateur et n'affiche aucun message.
	 * @param decrBy Montant à créditer
	 * @return le nouveau nombre de coins du joueur
	 */
	public abstract long decreaseCoins(long decrBy);

	/**
	 * Effectue un crédit de coins au joueur
	 * @param amount Montant à créditer
	 * @param reason Raison du crédit
	 */
	public void creditCoins(long amount, String reason) {
		creditCoins(amount, reason, true, null);
	}

	/**
	 * Effectue un crédit de coins au joueur
	 * @param amount Montant à créditer
	 * @param reason Raison du crédit
	 * @param applyMultiplier Appliquer les multiplicateurs du joueurs
	 */
	public void creditCoins(long amount, String reason, boolean applyMultiplier) {
		creditCoins(amount, reason, applyMultiplier, null);
	}

	/**
	 * Effectue un crédit de coins au joueur
	 * @param amount Montant à créditer
	 * @param reason Raison du crédit
	 * @param financialCallback Callback appellé après l'opération
	 */
	public void creditCoins(long amount, String reason, FinancialCallback<Long> financialCallback) {
		creditCoins(amount, reason, true, financialCallback);
	}

	/**
	 * Effectue un crédit de coins au joueur
	 * @param amount Montant à créditer
	 * @param reason Raison du crédit
	 * @param applyMultiplier Appliquer les multiplicateurs du joueurs
	 * @param financialCallback Callback appellé après l'opération
	 */
	public abstract void creditCoins(long amount, String reason, boolean applyMultiplier, FinancialCallback<Long> financialCallback);

	/**
	 * Effectue un débit de coins
	 * @param amount Montant à débiter
	 */
	public void withdrawCoins(long amount) {
		withdrawCoins(amount, null);
	}

	/**
	 * Effectue un débit de coins
	 * @param amount Montant à débiter
	 * @param financialCallback Callback appellé après l'opération
	 */
	public abstract void withdrawCoins(long amount, FinancialCallback<Long> financialCallback);

	/**
	 * Permet de savoir si un joueur a assez de coins pour acheter un produit
	 * @param amount Prix du produit
	 * @return true si le joueur a assez de coins, false sinon
	 */
	public boolean hasEnoughCoins(long amount) {
		return getCoins() >= amount;
	}

	/*
	Stars management
	 */

	public long getStars() {
		return getLong("stars", 0L);
	}

	public abstract long increaseStars(long incrBy);

	public abstract long decreaseStars(long decrBy);

	public void creditStars(long amount, String reason) {
		creditStars(amount, reason, true, null);
	}

	public void creditStars(long amount, String reason, boolean applyMultiplier) {
		creditStars(amount, reason, applyMultiplier, null);
	}

	public void creditStars(long amount, String reason, FinancialCallback<Long> financialCallback) {
		creditStars(amount, reason, true, financialCallback);
	}

	public abstract void creditStars(long amount, String reason, boolean applyMultiplier, FinancialCallback<Long> financialCallback);

	public void withdrawStars(long amount) {
		withdrawStars(amount, null);
	}

	public abstract void withdrawStars(long amount, FinancialCallback<Long> financialCallback);

	public boolean hasEnoughStars(long amount) {
		return getStars() >= amount;
	}
}
