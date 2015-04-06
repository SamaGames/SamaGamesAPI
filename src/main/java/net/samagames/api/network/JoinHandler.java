package net.samagames.api.network;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface JoinHandler {

    /**
     * Called when an user requires to connect, by rightclicking a game sign for example.
     * @param player joining player
     * @param response response filled by previous handlers
     * @return filled answer
     */
    public default JoinResponse requestJoin(UUID player, JoinResponse response) {
        return response;
    }

    /**
     * Called when an user requires to connect with his party, by rightclicking a game sign for example. It's called instead of requestJoin.
     * @param partyLeader the leader of the party
     * @param partyMembers the list of the players of the party, including the leader
     * @param response The pre-filled response
     * @return Filled response
     */
    public default JoinResponse requestPartyJoin(UUID partyLeader, Set<UUID> partyMembers, JoinResponse response) {
        return response;
    }

    /**
     * Called when an user logins on the server. It the player didn't request join, requestjoin will be fired automatically before this.
     * @param player joining player
     */
    public default void onLogin(UUID player) {
    }

    /**
     * Called when an user joins the server
     * @param player joining player
     */
    public default void finishJoin(Player player) {
    }

    /**
     * Called when an user joins as a moderator. Called instead of `onLogin` and `finishJoin`
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



}
