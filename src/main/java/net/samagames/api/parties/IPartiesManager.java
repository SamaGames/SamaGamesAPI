package net.samagames.api.parties;

import java.util.HashMap;
import java.util.UUID;

/**
 * Parties manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IPartiesManager
{
    /**
     * Get the UUID of the given player's party
     *
     * @param player Player
     *
     * @return
     */
	UUID getPlayerParty(UUID player);

    /**
     * Get members of the given player's party
     *
     * @param party Party UUID {@link IPartiesManager##getPlayerParty(UUID)}
     *
     * @return A map with UUID and username
     */
	HashMap<UUID, String> getPlayersInParty(UUID party);

    /**
     * Get the server where the party is
     *
     * @param party Party UUID {@link IPartiesManager##getPlayerParty(UUID)}
     *
     * @return Server name
     */
	String getCurrentServer(UUID party);

    /**
     * Get the UUID of the leader of the party
     *
     * @param party Party UUID {@link IPartiesManager##getPlayerParty(UUID)}
     *
     * @return Leader UUID
     */
	UUID getLeader(UUID party);
}
