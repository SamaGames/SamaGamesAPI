package net.samagames.api.games;

/**
 * Game status enumeration
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public enum Status
{
	STARTING("starting", false),
	WAITING_FOR_PLAYERS("waitingForPlayers", true),
	READY_TO_START("readyToStart", true),
	IN_GAME("inGame", false),
	FINISHED("finished", false),
	REBOOTING("rebooting", false),
	NOT_RESPONDING("idle", false);

	private final String id;
	private final boolean allowJoin;

    /**
     * Constructor
     *
     * @param id Status ID who will be sent
     * @param allowJoin This status allow player join?
     */
	Status(String id, boolean allowJoin)
    {
		this.id = id;
		this.allowJoin = allowJoin;
	}

    /**
     * Return status's identifier
     *
     * @return Identifier
     */
	public String getIdentifier()
    {
		return this.id;
	}

    /**
     * Return if the status is allowing join
     *
     * @return {@code true} if allowing
     */
    public boolean isAllowJoin()
    {
        return this.allowJoin;
    }

	@Override
	public String toString()
    {
		return this.getIdentifier();
	}
}
