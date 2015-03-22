package net.samagames.api.gameapi.themachine.messages;

import net.samagames.api.gameapi.themachine.messages.templates.BasicMessageTemplate;
import net.samagames.api.gameapi.themachine.messages.templates.CustomWinTemplate;
import net.samagames.api.gameapi.themachine.messages.templates.PlayerLeaderboardWinTemplate;
import net.samagames.api.gameapi.themachine.messages.templates.PlayerWinTemplate;

public interface TemplateManager
{
    /**
     * Renvoi l'instance du modèle vide avec les bordures
     * @return L'instance du modèle
     */
    public BasicMessageTemplate getBasicMessageTemplate();

    /**
     * Renvoi l'instance du modèle de message de fin de jeu vide
     * @return L'instance du modèle
     */
    public CustomWinTemplate getCustomWinTemplate();

    /**
     * Renvoi l'instance du modèle de message de fin de jeu pour un joueur unique
     * @return L'instance du modèle
     */
    public PlayerWinTemplate getPlayerWinTemplate();

    /**
     * Renvoi l'instance ud modèle de message de fin de jeu pour 3 joueurs (classement)
     * @return L'instance du modèle
     */
    public PlayerLeaderboardWinTemplate getPlayerLeaderboardWinTemplate();
}
