package net.samagames.api.games.themachine.messages;

import org.bukkit.Bukkit;
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
