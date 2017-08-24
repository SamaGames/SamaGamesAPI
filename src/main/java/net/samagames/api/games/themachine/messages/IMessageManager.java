package net.samagames.api.games.themachine.messages;

import org.bukkit.OfflinePlayer;
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
public interface IMessageManager
{
    /**
     * Send a custom message with a given text
     *
     * @param text Text
     * @param gameTag Show game tag as prefix
     *
     * @return Instance of the message {@link Message}
     */
    Message writeCustomMessage(String text, boolean gameTag);

    /**
     * Send a welcome message for the given player
     * to all players
     *
     * @param player Joined player
     * @param playerCount Show the player count
     *
     * @return Instance of the message {@link Message}
     */
    Message writePlayerJoinToAll(Player player, boolean playerCount);

    /**
     * Send a welcome message to a given player
     *
     * @param player Joined player
     *
     * @return Instance of the message {@link Message}
     */
    Message writeWelcomeInGameToPlayer(Player player);

    /**
     * Send a formatted cooldown message to all players
     *
     * @param remainingTime Remaining time in second
     *
     * @return Instance of the message {@link Message}
     */
    Message writeGameStartIn(int remainingTime);

    /**
     * Send a message to all players that there is
     * no enough players to start the game
     **
     * @return Instance of the message {@link Message}
     */
    Message writeNotEnoughPlayersToStart();

    /**
     * Send a message to all players that the game starts
     **
     * @return Instance of the message {@link Message}
     */
    Message writeGameStart();

    /**
     * Send a the disconnection message of a given players
     * to all players
     *
     * @param player Leaved player
     *
     * @return Instance of the message {@link Message}
     */
    Message writePlayerQuited(Player player);

    /**
     * Send a the disconnection message of a given players
     * to all players. Also show the time remaining to
     * rejoin the game
     *
     * @param player Leaved player
     * @param remainingTime Time remaining
     *
     * @return Instance of the message {@link Message}
     */
    Message writePlayerDisconnected(Player player, int remainingTime);

    /**
     * Send a the reconnection message of a given players
     * to all players
     *
     * @param player Rejoined player
     *
     * @return Instance of the message {@link Message}
     */
    Message writePlayerReconnected(Player player);

    /**
     * Send a message to all players that a given player
     * cannot rejoin the game anymore
     *
     * @param player Leaved player {@link OfflinePlayer}
     *
     * @return Instance of the message {@link Message}
     */
    Message writePlayerReconnectTimeOut(OfflinePlayer player);
}