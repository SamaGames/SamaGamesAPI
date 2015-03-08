package net.samagames.api.games;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface GameInfo {

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
