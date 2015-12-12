package net.samagames.api.network;

import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Join manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IJoinManager extends Listener
{
    /**
     * Register a JoinHandler which is going to be called by the manager
     *
     * @param handler The handler
     * @param priority The handler priority (0 = Lowest, please do not use priorities under 10)
     */
    void registerHandler(IJoinHandler handler, int priority);

    /**
     * Count connected players
     *
     * @return Number of players
     */
    int countExpectedPlayers();

    /**
     * Get connected players as a list of UUID
     *
     * @return List of UUID
     */
    HashSet<UUID> getExpectedPlayers();

    /**
     * Get connected moderator as a list of UUID
     *
     * @return List of UUID
     */
    List<UUID> getModeratorsExpected();
}
