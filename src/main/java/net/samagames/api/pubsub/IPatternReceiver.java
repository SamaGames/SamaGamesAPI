package net.samagames.api.pubsub;

/**
 * Pattern receiver class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPatternReceiver
{
	/**
	 * Fired when a Redis PubSub message is received
     *
     * @param pattern PubSub message's pattern
	 * @param channel PubSub message's channel
	 * @param packet PubSub message's content
	 */
    void receive(String pattern, String channel, String packet);
}
