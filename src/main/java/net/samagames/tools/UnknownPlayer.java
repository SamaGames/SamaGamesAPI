package net.samagames.tools;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by zyuiop on 24/09/14.
 */
public class UnknownPlayer {

    private UUID playerId;
    private String playerName;

    public UnknownPlayer(UUID playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public UnknownPlayer(Player player) {
        this.playerId = player.getUniqueId();
        this.playerName = player.getName();
    }

    public UnknownPlayer() {

    }

    public UnknownPlayer(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return playerId+"|"+playerName;
    }

    @Override
    public boolean equals(Object compare) {
        if (compare instanceof UnknownPlayer)
            return ((UnknownPlayer) compare).getPlayerId().equals(playerId);
        else if (compare instanceof UUID)
            return ((UUID) compare).equals(playerId);
        else
            return false;
    }
}
