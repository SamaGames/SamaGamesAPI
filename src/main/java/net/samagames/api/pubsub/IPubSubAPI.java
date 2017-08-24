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
