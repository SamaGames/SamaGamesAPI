package net.samagames.tools;

/**
 * Json private message object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class JsonPrivateMessage
{
    private UnknownPlayer sender;
    private UnknownPlayer receiver;
    private String message;

    /**
     * Constructor
     *
     * @param sender Sender of the private message
     * @param receiver Receiver of the private message
     * @param message Message's content
     */
    public JsonPrivateMessage(UnknownPlayer sender, UnknownPlayer receiver, String message)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * Get the sender of the message
     *
     * @return Sender
     */
    public UnknownPlayer getSender()
    {
        return this.sender;
    }

    /**
     * Get the receiver of the message
     *
     * @return Receiver
     */
    public UnknownPlayer getReceiver()
    {
        return this.receiver;
    }

    /**
     * Get message's content
     *
     * @return Message's content
     */
    public String getMessage()
    {
        return this.message;
    }
}
