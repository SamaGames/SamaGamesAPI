package net.samagames.api.gameapi.themachine;

import net.samagames.api.gameapi.Game;
import net.samagames.api.gameapi.themachine.messages.MessageManager;
import net.samagames.api.gameapi.themachine.messages.TemplateManager;

public interface CoherenceMachine
{
    /**
     * Renvoi le tag du jeu pour les messages dans le chat
     * @return Tag du jeu
     */
    public String getGameTag();

    /**
     * Renvoi l'instance du manager de messages basics
     * @return Manager
     */
    public MessageManager getMessageManager();

    /**
     * Renvoi l'instance du manager de modèles de messages
     * @return Manager
     */
    public TemplateManager getTemplateManager();

    /**
     * Renvoi l'objet du jeu
     * @return L'objet du jeu
     */
    public Game getGame();
}
