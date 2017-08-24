package net.samagames.tools;

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
