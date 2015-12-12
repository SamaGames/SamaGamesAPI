package net.samagames.api.pubsub;

/**
 * Packets receiver class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPacketsReceiver
{
    /**
     * Fired when a Redis PubSub message is received
     *
     * @param channel PubSub message's channel
     * @param packet PubSub message's content
     */
    void receive(String channel, String packet);
}
