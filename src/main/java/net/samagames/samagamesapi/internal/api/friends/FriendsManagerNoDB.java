package net.samagames.samagamesapi.internal.api.friends;

import net.samagames.samagamesapi.api.friends.FriendsManager;

import java.util.*;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class FriendsManagerNoDB implements FriendsManager {
	@Override
	public boolean areFriends(UUID p1, UUID p2) {
		return false;
	}

	@Override
	public List<String> namesFriendsList(UUID asking) {
		return new ArrayList<>();
	}

	@Override
	public List<UUID> uuidFriendsList(UUID asking) {
		return new ArrayList<>();
	}

	@Override
	public Map<UUID, String> associatedFriendsList(UUID asking) {
		return new HashMap<>();
	}

	@Override
	public List<String> requests(UUID asking) {
		return new ArrayList<>();
	}

	@Override
	public List<String> sentRequests(UUID asking) {
		return new ArrayList<>();
	}
}
