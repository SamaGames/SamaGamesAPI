package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.google.gson.Gson;
import org.bukkit.entity.Player;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class JsonModMessage
{
	protected String sender;
    protected ModChannel modChannel;
	protected ChatColor senderPrefix;
	protected String message;

    /**
     * Constructor
     *
     * @param sender Sender of the moderator message
     * @param modChannel Channel of the moderator message
     * @param senderPrefix Prefix color of the sender
     * @param message Message content
     */
	public JsonModMessage(String sender, ModChannel modChannel, ChatColor senderPrefix, String message)
    {
		this.sender = sender;
        this.modChannel = modChannel;
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
    @Deprecated
	public static JsonModMessage build(CommandSender sender, String message)
    {
		return build(sender, ModChannel.DISCUSSION, message);
	}

    /**
     * Create an instance of a message with a given sender
     * and message
     *
     * @param sender Sender of the moderator message
     * @param modChannel Channel of the moderator message
     * @param message Prefix color of the sender
     *
     * @return New instance
     */
    public static JsonModMessage build(CommandSender sender, ModChannel modChannel, String message)
    {
        if (sender instanceof Player)
        {
            String prefix ="";//TODO SamaGamesAPI.get().getPermissionsManager().getPrefix(SamaGamesAPI.get().getPermissionsManager().getApi().getUser(((Player) sender).getUniqueId()));
            ChatColor pr = (prefix == null) ? ChatColor.AQUA : ChatColor.getByChar(prefix.charAt(prefix.length() - 1));

            return new JsonModMessage(sender.getName(), modChannel, pr, message);
        }
        else
        {
            return new JsonModMessage(sender.getName(), modChannel, ChatColor.AQUA, message);
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
     * Set the channel of the moderator message
     *
     * @param modChannel Message's channel
     */
    public void setModChannel(ModChannel modChannel)
    {
        this.modChannel = modChannel;
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
     * Get the channel of the moderator message
     *
     * @return Message's channel
     */
    public ModChannel getModChannel()
    {
        return this.modChannel;
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
