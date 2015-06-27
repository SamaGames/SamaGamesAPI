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
public enum Status
{
	STARTING("starting"),
	WAITING_FOR_PLAYERS("waitingForPlayers"),
	READY_TO_START("readyToStart"),
	IN_GAME("inGame"),
	FINISHED("finished"),
	REBOOTING("rebooting"),
	NOT_RESPONDING("idle");

	private final String identifier;

    Status(String identifier)
    {
		this.identifier = identifier;
	}

	public static Status fromString(String str)
    {
		for (Status status : Status.values())
			if (status.getIdentifier().equals(str))
				return status;

		return null;
	}

	public String getIdentifier()
    {
		return this.identifier;
	}

	@Override
	public String toString()
    {
		return this.getIdentifier();
	}
}
