package net.samagames.api.network;

import org.bukkit.entity.Player;

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
public interface IJoinHandler
{
    /**
     * Called when an user wanted to connect, by right clicking a game sign for example.
     * (Before player is ont the server)
     *
     * @param player Asking player
     * @param response Response object {@link JoinResponse}
     *
     * @return Filled response
     */
    default JoinResponse requestJoin(UUID player, JoinResponse response)
    {
        return response;
    }

    /**
     * Called when a party wanted to connect, by right clicking a game sign for example.
     * (Before player is on the server)
     *
     * @param party Party uuid
     * @param player The player who join
     * @param response Response object {@link JoinResponse}
     *
     * @return Filled response
     */
    default JoinResponse requestPartyJoin(UUID party, UUID player, JoinResponse response)
    {
        return response;
    }

    /**
     * Event fired when a player login
     *
     * @param player Joined player's UUID
     * @param username Joined player's username
     */
    default void onLogin(UUID player, String username) {}

    /**
     * Event fired when a player logged
     *
     * @param player Joined player's UUID
     */
    default void finishJoin(Player player) {}

    /**
     * Event fired when a moderator login
     *
     * <b>Override {@link IJoinHandler#onLogin(UUID, String)} and {@link IJoinHandler#finishJoin(Player)}</b>
     *
     * @param player Joined moderator
     */
    default void onModerationJoin(Player player) {}

    /**
     * Event fired when a player logout
     *
     * @param player Joined player
     */
    default void onLogout(Player player) {}
}
