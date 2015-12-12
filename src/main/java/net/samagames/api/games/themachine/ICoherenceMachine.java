package net.samagames.api.games.themachine;

import net.samagames.api.games.Game;
import net.samagames.api.games.IGameProperties;
import net.samagames.api.games.themachine.messages.IMessageManager;
import net.samagames.api.games.themachine.messages.ITemplateManager;
import org.bukkit.inventory.ItemStack;

/**
 * Coherence Machine class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface ICoherenceMachine
{
    /**
     * Override the sub-title message when there is less
     * than 5seconds remaining before the game starts.
     *
     * @param phrase Catch phrase
     */
    void setStartCountdownCatchPhrase(String phrase);

    /**
     * Override the game's prefix in chat messages.
     *
     * @param shortcut Shortcut
     */
    void setNameShortcut(String shortcut);

    /**
     * Get the game tag
     *
     * @return Game tag
     */
    String getGameTag();

    /**
     * Get the message manager {@link IMessageManager}
     *
     * @return Message manager
     */
    IMessageManager getMessageManager();

    /**
     * Get the template manager {@link ITemplateManager}
     *
     * @return Manager
     */
    ITemplateManager getTemplateManager();

    /**
     * Get the registered game {@link Game}
     *
     * @return Game
     */
    Game getGame();

    /**
     * Get the game's properties
     *
     * @return Game properties object {@link IGameProperties}
     */
    IGameProperties getGameProperties();

    /**
     * Get the ItemStack {@link ItemStack} of the leave door
     *
     * @return Door
     */
    ItemStack getLeaveItem();

    /**
     * Get the sub-title message when there is less
     * than 5seconds remaining before the game starts.
     *
     * @return Catch phrase
     */
    String getStartCountdownCatchPhrase();

    /**
     * Get the game's prefix in chat messages.
     *
     * @return Shortcut
     */
    String getNameShortcut();
}
