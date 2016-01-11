package net.samagames.api.network;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * Join handler class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IJoinHandler
{
    /**
     * Called when an user wanted to connect, by right clicking a game sign for example.
     *
     * @param player Asking player
     * @param response Response object {@link JoinResponse}
     *
     * @return Filled response
     */
    default JoinResponse requestJoin(UUID player, JoinResponse response)
    {
        return response;
    }

    /**
     * Called when a party wanted to connect, by right clicking a game sign for example.
     *
     * @param party Party uuid
     * @param player The player who join
     * @param response Response object {@link JoinResponse}
     *
     * @return Filled response
     */
    default JoinResponse requestPartyJoin(UUID party, UUID player, JoinResponse response)
    {
        return response;
    }

    /**
     * Event fired when a player login
     *
     * @param player Joined player's UUID
     * @param username Joined player's username
     */
    default void onLogin(UUID player, String username) {}

    /**
     * Event fired when a player logged
     *
     * @param player Joined player's UUID
     */
    default void finishJoin(Player player) {}

    /**
     * Event fired when a moderator login
     *
     * <b>Override {@link IJoinHandler#onLogin(UUID, String)} and {@link IJoinHandler#finishJoin(Player)}</b>
     *
     * @param player Joined moderator
     */
    default void onModerationJoin(Player player) {}

    /**
     * Event fired when a player logout
     *
     * @param player Joined player
     */
    default void onLogout(Player player) {}
}
