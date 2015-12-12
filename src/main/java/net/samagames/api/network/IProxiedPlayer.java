package net.samagames.api.network;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

/**
 * Proxied player object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IProxiedPlayer
{
    /**
     * Returns the current server name for the player
     *
     * @return Server's name
     */
    String getServer();

    /**
     * Gives the name of the proxy the player is connected to
     *
     * @return Proxy's name
     */
    String getProxy();

    /**
     * Gives the ip of the player
     *
     * @return IP address
     */
    String getIp();

    /**
     * Gives the UUID of the player
     *
     * @return UUID
     */
    UUID getUUID();

    /**
     * Gives the username of the player
     *
     * @return Username
     */
    String getName();

    /**
     * Disconnects the player from the proxy with the given reason
     *
     * @param reason Reason {@link TextComponent}
     */
    void disconnect(TextComponent reason);

    /**
     * Connects the player to a given server
     *
     * @param server The server
     */
    void connect(String server);

    /**
     * Send a message to the player
     *
     * @param component The message {@link TextComponent}
     */
    void sendMessage(TextComponent component);
}
