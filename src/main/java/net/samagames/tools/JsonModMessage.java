package net.samagames.tools;

import net.md_5.bungee.api.ChatColor;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.command.CommandSender;
import com.google.gson.Gson;
import org.bukkit.entity.Player;

/**
 * Json mod message object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class JsonModMessage
{
	protected String sender;
	protected ChatColor senderPrefix;
	protected String message;

    /**
     * Constructor
     *
     * @param sender Sender of the moderator message
     * @param senderPrefix Prefix color of the sender
     * @param message Message content
     */
	public JsonModMessage(String sender, ChatColor senderPrefix, String message)
    {
		this.sender = sender;
		this.senderPrefix = senderPrefix;
		this.message = message;
	}

    /**
     * Create an instance of a message with a given sender
     * and message
     *
     * @param sender Sender of the moderator message
     * @param message Prefix color of the sender
     *
     * @return New instance
     */
	public static JsonModMessage build(CommandSender sender, String message)
    {
		if (sender instanceof Player)
        {
			String prefix = SamaGamesAPI.get().getPermissionsManager().getPrefix(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(((Player) sender).getUniqueId()));
			ChatColor pr = (prefix == null) ? ChatColor.AQUA : ChatColor.getByChar(prefix.charAt(prefix.length() - 1));

			return new JsonModMessage(sender.getName(), pr, message);
		}
        else
        {
			return new JsonModMessage(sender.getName(), ChatColor.AQUA, message);
		}
	}

    /**
     * Send the moderator message
     */
    public void send()
    {
        SamaGamesAPI.get().getPubSub().send("moderationchan", new Gson().toJson(this));
    }

    /**
     * Set the sender of the message
     *
     * @param sender Message's sender
     */
    public void setSender(String sender)
    {
        this.sender = sender;
    }

    /**
     * Set the prefix color of the sender
     *
     * @param senderPrefix Prefix's color
     */
    public void setSenderPrefix(ChatColor senderPrefix)
    {
        this.senderPrefix = senderPrefix;
    }

    /**
     * Set the content of the message
     *
     * @param message Message's content
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Get the sender of the message
     *
     * @return Message's sender
     */
	public String getSender()
    {
		return this.sender;
	}

    /**
     * Get the prefix color of the sender
     *
     * @return Prefix's color
     */
	public ChatColor getSenderPrefix()
    {
		return this.senderPrefix;
	}

    /**
     * Get the content of the message
     *
     * @return Message's content
     */
	public String getMessage()
    {
		return this.message;
	}
}
