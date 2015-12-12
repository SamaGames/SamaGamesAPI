package net.samagames.api.pubsub;

/**
 * Sender class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class PendingMessage
{
	private final String channel;
	private final String message;
	private final Runnable callback;

    /**
     * Constructor
     *
     * @param channel Message's channel
     * @param message Message's content
     * @param callback Callback fired after the operation
     */
	public PendingMessage(String channel, String message, Runnable callback)
    {
		this.channel = channel;
		this.message = message;
		this.callback = callback;
	}

    /**
     * Constructor
     *
     * @param channel Message's channel
     * @param message Message's content
     */
    public PendingMessage(String channel, String message)
    {
        this(channel, message, null);
    }

    /**
     * Fire callback
     */
    public void runAfter()
    {
        try
        {
            if (this.callback != null)
                this.callback.run();
        }
        catch (Exception ignored) {}
    }

    /**
     * Get message's channel
     *
     * @return Channel
     */
	public String getChannel()
    {
		return this.channel;
	}

    /**
     * Get message's content
     *
     * @return Message
     */
	public String getMessage()
    {
		return this.message;
	}
}