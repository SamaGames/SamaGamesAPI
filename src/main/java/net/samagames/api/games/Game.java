package net.samagames.api.games;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface Game {

	/**
	 * Méthode appelée par l'API lors du LoginEvent
	 * Cette méthode n'est pas appelée si le joueur rejoint en mode modération
	 * @param player	Joueur tentant de rejoindre la partie
	 * @param response	Réponse de connexion, préremplie par l'API
	 * @return réponse de connexion, modifiée si besoin
	 */
	public JoinResponse preJoinPlayer(UUID player, JoinResponse response);

	/**
	 * Méthode appelée par l'API lors du PlayerJoinEvent
	 * Cette méthode n'est pas appelée si le joueur rejoint en mode modération
	 * @param player	Joueur tentant de rejoindre la partie
	 * @param response	Réponse de connexion, préremplie par l'API
	 * @return réponse de connexion, modifiée si besoin
	 */
	public JoinResponse joinPlayer(Player player, JoinResponse response);

	/**
	 * Méthode appelée par l'API lors du JoinEvent si un joueur tente de rejoindre en tant que modérateur
	 * @param player	Joueur rejoignant la partie
	 */
	public void joinModeration(Player player);

	/**
	 * Méthode appelée lors de la déconnexion d'un joueur
	 * @param player	Joueur venant de se déconnecter
	 */
	public void logout(Player player);

	/**
	 * Display a message, prepending it with the game tag.
	 * @param message Message to display
	 */
	public void displayMessage(String message);

	/**
	 * Must return the amount of players in the game (playing + spectating), moderators excluded.
	 * @return
	 */
	public int getConnectedPlayers();

	/**
	 * Must return the amount of players currently still playing
	 * @return
	 */
	public int getIngamePlayers();

	/**
	 * Must return the maximum amount of normal players allowed
	 * @return
	 */
	public int getMaxPlayers();

	/**
	 * Must return the maximum amount of players allowed (players + VIP/VIP+)
	 * @return
	 */
	public int getTotalMaxPlayers();


	public String getMapName();

	/**
	 * Returns the current gamestate. It's only used to check if an user can join the arena.
	 * @return
	 */
	public GameState getState();


	public enum GameState {
		/**
		 * The game is not ready to host players
		 */
		NOT_READY,
		/**
		 * Players are allowed to join
		 */
		JOINING,
		/**
		 * The game is started, players cannot join
		 */
		INGAME
	}
}
