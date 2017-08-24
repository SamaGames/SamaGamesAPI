package net.samagames.api.parties;

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
public interface IParty {

    /**
     * GEt the party UUID
     * @return UUID of the current party
     */
    UUID getParty();

    /**
     * Get the Leader UUID
     * @return UUID of the leader party
     */
    UUID getLeader();

    /**
     * Change the leader of the party
     * @param leader UUID of the new suprem leader
     */
    void setLeader(UUID leader);

    /**
     * Check if the player is in this party
     * @param player UUID of the player to check
     * @return boolean (true if the player is in the party, false otherwise)
     */
    boolean containsPlayer(UUID player);

    /**
     * Get all players present in the party, some player may not be online so be carefull
     * @return List of UUID players
     */
    List<UUID> getPlayers();

}
