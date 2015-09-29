package net.samagames.api.games.themachine;

import net.samagames.api.games.Game;
import net.samagames.api.games.IGameProperties;
import net.samagames.api.games.themachine.messages.IMessageManager;
import net.samagames.api.games.themachine.messages.ITemplateManager;
import org.bukkit.inventory.ItemStack;

public interface ICoherenceMachine
{
    /**
     * Définir le message sous le compteur au début de la partie (< 5 secondes)
     * @param phrase La phrase
     */
    void setStartCountdownCatchPhrase(String phrase);

    /**
     * Définir une abréviation pour le nom du jeu dans le chat
     * @param shortcut L'abréviation
     */
    void setNameShortcut(String shortcut);

    /**
     * Renvoi le tag du jeu pour les messages dans le chat
     * @return Tag du jeu
     */
    String getGameTag();

    /**
     * Renvoi l'instance du manager de messages basics
     * @return Manager
     */
    IMessageManager getMessageManager();

    /**
     * Renvoi l'instance du manager de modèles de messages
     * @return Manager
     */
    ITemplateManager getTemplateManager();

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

    /**
     * Renvoi le message sous le compteur au début de la partie (< 5 secondes)
     * @return Le message
     */
    String getStartCountdownCatchPhrase();

    /**
     * Renvoi l'abréviation du nom du jeu dans le chat
     * @return L'abréviation
     */
    String getNameShortcut();
}
