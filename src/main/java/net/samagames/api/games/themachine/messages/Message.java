package net.samagames.api.games.themachine.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Message object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Message
{
    private final String text;
    private final String gameTag;

    /**
     * Constructor with a specified prefix
     *
     * @param text Text of the message
     * @param gameTag Prefix of the message (game tag)
     */
    public Message(String text, String gameTag)
    {
        this.text = text;
        this.gameTag = gameTag;
    }

    /**
     * Constructor with no prefix
     *
     * @param text Text of the message
     */
    public Message(String text)
    {
        this(text, null);
    }

    /**
     * Send the message to all players
     *
     * @return This message
     */
    public Message displayToAll()
    {
        Bukkit.broadcastMessage((this.gameTag != null ? this.gameTag  + " " : "") + this.text);
        return this;
    }

    /**
     * Send the message to a given player
     *
     * @return This message
     */
    public Message display(Player player)
    {
        player.sendMessage((this.gameTag != null ? this.gameTag + " " : "") + this.text);
        return this;
    }

    /**
     * Get the text of the message
     *
     * @return Text
     */
    public String getText()
    {
        return this.text;
    }

    @Override
    public String toString()
    {
        return this.text;
    }
}
