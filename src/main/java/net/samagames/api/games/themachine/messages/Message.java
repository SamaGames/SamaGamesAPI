package net.samagames.api.games.themachine.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Message
{
    private final String text;
    private final String gameTag;

    public Message(String text, String gameTag)
    {
        this.text = text;
        this.gameTag = gameTag;
    }

    public Message(String text)
    {
        this(text, null);
    }

    public Message displayToAll()
    {
        Bukkit.broadcastMessage((this.gameTag != null ? this.gameTag : "") + this.text);
        return this;
    }

    public Message display(Player player)
    {
        player.sendMessage((this.gameTag != null ? this.gameTag : "") + this.text);
        return this;
    }

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
