package net.samagames.api.player;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

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
public interface IPlayerDataManager
{

	/**
	 * Get given player's data
     *
	 * @param player Player's UUID
     *
	 * @return Instance
	 */
    AbstractPlayerData getPlayerData(UUID player);

    /**
     * Get given player's data
     *
     * @param player Player's UUID
     * @param forceRefresh Refresh the data before return
     *
     * @return Instance
     */
    AbstractPlayerData getPlayerData(UUID player, boolean forceRefresh);

	/**
	 * Kick the player from the network (need to add sanction manually)
	 *
	 * @param reason Message to show
	 *
	 */
	void kickFromNetwork(UUID player, TextComponent reason);

	/**
	 * Send a player to a specific server with the name
	 *
	 * @param server Server name
	 *
	 */
	void connectToServer(UUID player, String server);

	/**
	 * Send a message using the proxy (maybe useless ?)
	 *
	 * @param component Message
	 *
	 */
	void sendMessage(UUID player, TextComponent component);
}
