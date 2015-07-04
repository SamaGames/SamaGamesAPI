package net.samagames.api.games;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface IGame
{
    void startGame();

    void handlePostRegistration();

    void handleLogin(Player player, boolean reconnect);

    void handleModeratorLogin(Player player);

    void handleLogout(Player player);

    void handleReconnect(Player player);

    void handleReconnectTimeOut(Player player);

    void handleGameEnd();

    void addCoins(Player player, int coins, String reason);

    void addStars(Player player, int stars, String reason);

    void increaseStat(UUID uuid, String statName, int count);

    void setSpectator(Player player);

    void setStatus(Status status);

    String getGameName();

    Status getStatus();

    int getConnectedPlayers();

    /**
     * Savoir si un joueur est en jeu ou non
     *
     * @param player Le joueur en question
     * @return Oui ou non
     */
    boolean hasPlayer(Player player);

    /**
     * Savoir si un joueur peut-entrer en jeu
     *
     * @param player Le joueur en question
     * @param reconnect Si le joueur se reconnecte pendant le jeu ou non
     * @return Un objet Pair de la forme <Autorisé ? (Oui/Non), Raison du refus (Si refus)>
     */
    Pair<Boolean, String> canJoinGame(UUID player, boolean reconnect);

    /**
     * Savoir si une partie peut entrer entière en jeu
     *
     * @param partyMembers Les joueurs de la partie
     * @return Un objet Pair de la forme <Autorisé ? (Oui/Non), Raison du refus (Si refus)>
     */
    Pair<Boolean, String> canPartyJoinGame(Set<UUID> partyMembers);

    /**
     * Savoir si un joueur est un spectateur
     *
     * @param player Le joueur en question
     * @return Oui ou non
     */
    boolean isSpectator(Player player);
}
