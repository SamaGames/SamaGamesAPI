package net.samagames.api.network;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public interface JoinHandler {

    public default JoinResponse onLogin(UUID player, JoinResponse response) {
        return response;
    }


    public default JoinResponse onJoin(Player player, JoinResponse response) {
        return response;
    }

    public default void onModerationJoin(Player player) {

    }

    public default void onLogout(Player player) {

    }

    public default JoinResponse onPreJoinParty(HashSet<UUID> players, JoinResponse response) {
        return response;
    }

}
