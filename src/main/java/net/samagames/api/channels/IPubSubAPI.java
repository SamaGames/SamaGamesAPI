package net.samagames.api.channels;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IPubSubAPI {

	/**
	 * Enregistre un listener sur le channel pubsub passé en argument
	 * @param channel 	Nom du channel à écouter
	 * @param receiver 	Objet recevant les packets
	 */
	public void subscribe(String channel, IPacketsReceiver receiver);

	void subscribe(String pattern, IPatternReceiver receiver);

	/**
	 * Envoie un message sur un channel pubsub
	 * @param channel	Channel sur lequel envoyer le message
	 * @param message	Message à publier
	 */
	public void send(String channel, String message);

	void send(PendingMessage message);

	public ISender getSender();

}
