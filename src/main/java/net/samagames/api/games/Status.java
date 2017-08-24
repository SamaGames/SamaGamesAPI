package net.samagames.api.games;

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
