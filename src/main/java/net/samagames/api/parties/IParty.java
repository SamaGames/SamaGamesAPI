package net.samagames.api.parties;

import java.util.List;
import java.util.UUID;

/**
 * Created by Silvanosky on 10/04/2016.
 */

/**
 * Represent a local party on the server
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
