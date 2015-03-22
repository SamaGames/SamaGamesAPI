package net.samagames.api.gameapi.themachine.messages;

import org.bukkit.entity.Player;

public interface MessageManager
{
    /**
     * Envoi un message à tout le monde montrant la connexion d'un joueur
     * @param player Le joueur venant de se connecter
     * @return L'objet du message
     */
    public Message writePlayerJoinToAll(Player player);

    /**
     * Envoi un message au joueur pour l'accueillir en jeu
     * @param player Le joueur venant de se connecter
     * @return L'objet du message
     */
    public Message writeWelcomeInGameToPlayer(Player player);

    /**
     * Envoi le temps restant avant le début de la partie à tout le monde
     * @param remainingTime Le temps restant
     * @return L'objet du message
     */
    public Message writeGameStartIn(int remainingTime);

    /**
     * Envoi qu'il n'y a plus assez de joueurs pour commencer le jeu à tout le monde
     * @return L'objet du message
     */
    public Message writeNotEnougthPlayersToStart();

    /**
     * Envoi le message de début de jeu à tout le monde
     * @return L'objet du message
     */
    public Message writeGameStart();

    /**
     * Envoi aux joueurs le message de déconnexion d'un joueur
     * @param player Le joueur déconnecté
     * @return L'objet du message
     */
    public Message writePlayerQuitted(Player player);

    /**
     * Envoi aux joueurs le message de déconnexion temporaire ou non d'un joueur si cela est possible
     * @param player Le joueur déconnecté
     * @param remainingTime Le temps que possède le joueur pour se reconnecté (en minutes)
     * @return L'objet du message
     */
    public Message writePlayerDisconnected(Player player, int remainingTime);

    /**
     * Envoi aux joueurs le message de reconnexion d'un joueur au jeu si cela est possible
     * @param player Le joueur reconnecté
     * @return L'objet du message
     */
    public Message writePlayerReconnedted(Player player);

    /**
     * Renvoi le message comme quoi l'arène de jeu est pleine
     * @return L'objet du message
     */
    public Message getArenaFull();
}