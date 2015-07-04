package net.samagames.api.network;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface IProxiedPlayer {

	/**
	 * Returns the current server name for the player
	 * @return the server name
	 */
	public String getServer();

	/**
	 * Gives the name of the proxy the player is connected to
	 * @return the proxy name
	 */
	public String getProxy();

    /**
     * Gives the ip of the player
     * @return the ip
     */
    public String getIp();

    /**
     * Gives the UUID of the player
     * @return the uuid
     */
    public UUID getUUID();

    /**
     * Gives the name of the player
     * @return the name
     */
    public String getName();

	/**
	 * Disconnects the player from the proxy with the given reason
	 * @param reason The reason component
	 */
	public void disconnect(TextComponent reason);

	/**
	 * Connects the player to a server
	 * @param server The server you want to connect the player to
	 */
	public void connect(String server);

	/**
	 * Connects the player to a server. This method asks the server to connect the player, which means the server will check the player ability to join AND if he can join he will get moved.
	 * @param game The server you want to connect the player to
	 */
	public void connectGame(String game);

	/**
	 * Send a message to the player
	 * @param component The message you want to send
	 */
	public void sendMessage(TextComponent component);

}
