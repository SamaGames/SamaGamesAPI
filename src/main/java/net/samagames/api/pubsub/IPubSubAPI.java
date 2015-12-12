package net.samagames.api.pubsub;

/**
 * PubSub API class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPubSubAPI
{
	/**
	 * Subscribe a given {@link IPacketsReceiver} to a given channel
     *
	 * @param channel Channel to listen
	 * @param receiver Receiver
	 */
	void subscribe(String channel, IPacketsReceiver receiver);

    /**
     * Subscribe a given {@link IPatternReceiver} to a given pattern
     *
     * @param pattern Pattern to listen
     * @param receiver Receiver
     */
	void subscribe(String pattern, IPatternReceiver receiver);

	/**
	 * Send a given message into the given channel
     *
	 * @param channel Channel
	 * @param message Message
	 */
	void send(String channel, String message);

    /**
     * Send a PubSub message {@link PendingMessage}
     *
     * @param message Message
     */
	void send(PendingMessage message);

    /**
     * Get the message publisher
     *
     * @return Instance
     */
	ISender getSender();
}
