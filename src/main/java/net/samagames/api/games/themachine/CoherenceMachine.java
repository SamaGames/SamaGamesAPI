package net.samagames.api.games.themachine;

import net.samagames.api.games.IGameInfos;
import net.samagames.api.games.themachine.messages.MessageManager;
import net.samagames.api.games.themachine.messages.TemplateManager;

public interface CoherenceMachine
{
    /**
     * Renvoi le tag du jeu pour les messages dans le chat
     * @return Tag du jeu
     */
    String getGameTag();

    /**
     * Renvoi l'instance du manager de messages basics
     * @return Manager
     */
    MessageManager getMessageManager();

    /**
     * Renvoi l'instance du manager de modèles de messages
     * @return Manager
     */
    TemplateManager getTemplateManager();

    /**
     * Renvoi l'objet d'information de jeu
     * @return L'objet
     */
    IGameInfos getGameInfos();
}
