package net.samagames.api.games.themachine.messages;

import net.samagames.api.games.themachine.messages.templates.*;

public interface ITemplateManager
{
    /**
     * Renvoi l'instance du modèle vide avec les bordures
     * @return L'instance du modèle
     */
    BasicMessageTemplate getBasicMessageTemplate();

    /**
     * Renvoi l'instance du modèle de message de fin de jeu vide
     * @return L'instance du modèle
     */
    CustomWinTemplate getCustomWinTemplate();

    /**
     * Renvoi l'instance du modèle de message de fin de jeu pour un joueur unique
     * @return L'instance du modèle
     */
    PlayerWinTemplate getPlayerWinTemplate();

    /**
     * Renvoi l'instance du modèle de message de fin de jeu pour 3 joueurs (classement)
     * @return L'instance du modèle
     */
    PlayerLeaderboardWinTemplate getPlayerLeaderboardWinTemplate();

    /**
     * Renvoi l'instance du modèle de message d'annonce des gains
     * @return L'instance du modèle
     */
    EarningMessageTemplate getEarningMessageTemplate();
}
