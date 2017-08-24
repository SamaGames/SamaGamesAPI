package net.samagames.api.friends;

import java.util.List;
import java.util.Map;
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
public interface IFriendsManager
{
    /**
     * Determinate if two given players are friends
     *
     * @param p1 Player one
     * @param p2 Player two
     *
     * @return {@code true} if they are friends
     */
    boolean areFriends(UUID p1, UUID p2);

    /**
     * Get the given player's friends in a list of username
     *
     * @param asking Player
     *
     * @return A list of username
     */
    List<String> namesFriendsList(UUID asking);

    /**
     * Get the given player's friends in a list of UUID
     *
     * @param asking Player
     *
     * @return A list of UUID
     */
    List<UUID> uuidFriendsList(UUID asking);

    /**
     * Get the given player's friends in a list of UUID and username
     *
     * @param asking Player
     *
     * @return A map of UUID and username
     */
    Map<UUID, String> associatedFriendsList(UUID asking);

    /**
     * Get the given player's waiting friend requests
     *
     * @param asking Player
     *
     * @return A list of username
     */
    List<String> requests(UUID asking);

    /**
     * Get the given player's friend requests sent
     *
     * @param asking Player
     *
     * @return A list of username
     */
    List<String> sentRequests(UUID asking);

    /**
     * Delete the friendship betweet two given players
     *
     * @param asking Player one
     * @param target Player two
     *
     * @return Process result
     */
    boolean removeFriend(UUID asking, UUID target);
}
