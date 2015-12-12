package net.samagames.api.friends;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Friend manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
@Deprecated
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
