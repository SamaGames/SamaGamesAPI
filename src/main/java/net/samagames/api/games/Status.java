package net.samagames.api.games;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 *
 * This enum is provided as it. You can create your own status enum, if you think this one doesn't fit to your needs.
 *
 */
public enum Status {

	STARTING("starting", false),
	WAITING_FOR_PLAYERS("waitingForPlayers", true),
	READY_TO_START("readyToStart", true),
	IN_GAME("inGame", false),
	FINISHED("finished", false),
	REBOOTING("rebooting", false),
	NOT_RESPONDING("idle", false);

	private final String id;
	private final boolean allowJoin;

	Status(String id, boolean allowJoin) {
		this.id = id;
		this.allowJoin = allowJoin;
	}

	public static Status fromString(String str) {
		for (Status status : Status.values())
			if (status.getIdentifier().equals(str))
				return status;

		return null;
	}

	public boolean isAllowJoin() {
		return allowJoin;
	}

	public String getIdentifier() {
		return id;
	}

	@Override
	public String toString() {
		return getIdentifier();
	}
}
