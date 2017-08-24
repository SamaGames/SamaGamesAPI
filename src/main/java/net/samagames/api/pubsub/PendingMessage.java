package net.samagames.api.pubsub;

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