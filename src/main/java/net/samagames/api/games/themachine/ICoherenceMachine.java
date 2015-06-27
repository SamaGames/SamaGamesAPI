package net.samagames.api.games.themachine;

import net.samagames.api.games.IGameProperties;
import net.samagames.api.games.themachine.messages.MessageManager;
import net.samagames.api.games.themachine.messages.TemplateManager;
import net.samagames.core.api.games.Game;
import org.bukkit.inventory.ItemStack;

public interface ICoherenceMachine
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
    Game getGame();

    /**
     * Renvoi les propriétés du jeu
     * @return L'objet
     */
    IGameProperties getGameProperties();

    /**
     * Renvoi l'objet servant à quitter le jeu
     * @return L'objet
     */
    ItemStack getLeaveItem();
}
