package net.samagames.api.games;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface GameAPI {

	/**
	 * Définit le jeu sur lequel la GameAPI doit fonctionner et l'active. Il ne peut y en avoir qu'un à la fois.
	 * @param signZone	Zone de panneaux dans laquelle le jeu doit apparaître
	 * @param game		Objet représentant la partie
	 */
	public void enable(String signZone, Game game);

	/**
	 * Envoie un panneau de jeu vers les lobbys
	 * @param data	Panneau à envoyer
	 */
	public void sendSign(GameSignData data);

}
