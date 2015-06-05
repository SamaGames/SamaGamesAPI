package net.samagames.api.friends;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface FriendsManager {

	/**
	 * Permet de déterminer si deux joueurs sont amis
	 * @param p1 	Joueur a tester
	 * @param p2	Joueur a tester
	 * @return true si les joueurs sont amis, false sinon
	 */
	public boolean areFriends(UUID p1, UUID p2);

	/**
	 * Renvoie la liste des pseudos des amis du joueur
	 * @param asking	Joueur dont on veut la liste d'amis
	 * @return Une list de pseudos de joueurs
	 */
	public List<String> namesFriendsList(UUID asking);

	/**
	 * Permet d'obtenir la liste des amis d'un joueur (sous forme d'UUIDs)
	 * @param asking	Joueur dont on veut la liste d'amis
	 * @return Une liste d'uuids de joueurs
	 */
	public List<UUID> uuidFriendsList(UUID asking);

	/**
	 * Permet d'obtenir les amis d'un joueur, sous forme d'association UUID - Pseudo
	 * @param asking	Joueur dont on veut la liste d'amis
	 * @return Map d'amis au format UUID - PSEUDO
	 */
	public Map<UUID, String> associatedFriendsList(UUID asking);

	/**
	 * Obtient une liste des pseudos des personnes ayant envoyé une demande d'ami au joueur
	 * @param asking	Joueur dont on veut la liste de demandes reçues
	 * @return Les pseudos des joueurs ayant demandé ce joueur en ami
	 */
	public List<String> requests(UUID asking);

	/**
	 * Obtient une liste des demandes d'amis envoyées par ce joueur
	 * @param asking	Joueur dont on veut la liste de demandes envoyées
	 * @return Les pseudos des joueurs que ce joueur a demandé en ami
	 */
	public List<String> sentRequests(UUID asking);

}
