package net.samagames.api.network;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface JoinHandler {

    /**
     * Called when an user logins to the server. he's technically still on his previous server
     * @param player joining player
     * @param response response filled by previous handlers
     * @return filled answer
     */
    public default JoinResponse onLogin(UUID player, JoinResponse response) {
        return response;
    }

    /**
     * Called when an user joins the server
     * @param player joining player
     * @param response response filled by previous handlers
     * @return filled answer
     */
    public default JoinResponse onJoin(Player player, JoinResponse response) {
        return response;
    }

    /**
     * Called when an user joins as a moderator
     * @param player Joining player
     */
    public default void onModerationJoin(Player player) {

    }

    /**
     * Called when an user logs out
     * @param player Leaving player
     */
    public default void onLogout(Player player) {

    }

    /**
     * Called when a party join a game (called BEFORE onLogin)
     * @param players the list of the players of the party
     * @param response The pre-filled response
     * @return Filled response
     */
    public default JoinResponse onPreJoinParty(Set<UUID> players, JoinResponse response) {
        return response;
    }

}
