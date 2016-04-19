package net.samagames.api.parties;

import java.util.HashMap;
import java.util.List;
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
