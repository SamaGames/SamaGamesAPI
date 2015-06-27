package net.samagames.core.api.games.themachine;

import net.samagames.api.games.IGameProperties;
import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.MessageManager;
import net.samagames.api.games.themachine.messages.TemplateManager;
import net.samagames.core.api.games.AbstractGame;
import net.samagames.core.api.games.themachine.messages.MessageManagerImpl;
import net.samagames.core.api.games.themachine.messages.TemplateManagerImpl;
import org.bukkit.ChatColor;

public class CoherenceMachineImpl implements ICoherenceMachine
{
    private final AbstractGame game;
    private final IGameProperties gameProperties;
    private final MessageManager messageManager;
    private final TemplateManager templateManager;

    public CoherenceMachineImpl(AbstractGame game, IGameProperties gameProperties)
    {
        this.game = game;
        this.gameProperties = gameProperties;

        this.messageManager = new MessageManagerImpl(this);
        this.templateManager = new TemplateManagerImpl(this);
    }

    @Override
    public String getGameTag()
    {
        return ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + this.game.getGameName() + ChatColor.DARK_AQUA + "]" + ChatColor.RESET;
    }

    @Override
    public MessageManager getMessageManager()
    {
        return this.messageManager;
    }

    @Override
    public TemplateManager getTemplateManager()
    {
        return this.templateManager;
    }

    @Override
    public AbstractGame getGame()
    {
        return this.game;
    }

    @Override
    public IGameProperties getGameProperties()
    {
        return this.gameProperties;
    }
}
