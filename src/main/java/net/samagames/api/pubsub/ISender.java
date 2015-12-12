package net.samagames.api.pubsub;

/**
 * Sender class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface ISender
{
    /**
     * Publish a given message
     *
     * @param message Message
     */
	void publish(PendingMessage message);
}
