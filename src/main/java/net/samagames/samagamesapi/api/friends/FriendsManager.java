package net.samagames.samagamesapi.api.friends;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface FriendsManager {

	public boolean areFriends(UUID p1, UUID p2);
	public List<String> namesFriendsList(UUID asking);
	public List<UUID> uuidFriendsList(UUID asking);
	public Map<UUID, String> associatedFriendsList(UUID asking);
	public List<String> requests(UUID asking);
	public List<String> sentRequests(UUID asking);

}
