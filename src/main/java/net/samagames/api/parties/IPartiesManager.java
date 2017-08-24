package net.samagames.api.parties;

import java.util.HashMap;
import java.util.List;
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
public interface IPartiesManager
{
    /**
     * Get the UUID of the given player's party
     *
     * @param player Player
     *
     * @return The party object if one player in it is connected, null otherwise
     */
	IParty getPartyForPlayer(UUID player);

    IParty getParty(UUID partie);

    /**
     * Get members of the given player's party
     *
     * @param party Party UUID {@link IPartiesManager##getPlayerParty(UUID)}
     *
     * @return A list of UUID
     */
	List<UUID> getPlayersInParty(UUID party);

    /**
     * Get the server where the party is
     *
     * @param party Party UUID {@link IPartiesManager##getPlayerParty(UUID)}
     *
     * @return Server name
     */
    @Deprecated
	String getCurrentServer(UUID party);

    /**
     * Get the UUID of the leader of the party
     *
     * @param party Party UUID {@link IPartiesManager##getPlayerParty(UUID)}
     *
     * @return Leader UUID
     */
	UUID getLeader(UUID party);

    /**
     * Get all currently on this server (it's a copy)
     * @return Copied parties list (Party uuid, party)
     */
    HashMap<UUID, IParty> getParties();
}
