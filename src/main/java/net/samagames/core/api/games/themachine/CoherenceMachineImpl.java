package net.samagames.core.api.games.themachine;

import net.samagames.api.games.IGameInfos;
import net.samagames.api.games.themachine.CoherenceMachine;
import net.samagames.api.games.themachine.messages.MessageManager;
import net.samagames.api.games.themachine.messages.TemplateManager;
import net.samagames.core.api.games.themachine.messages.MessageManagerImpl;
import net.samagames.core.api.games.themachine.messages.TemplateManagerImpl;
import org.bukkit.ChatColor;

public class CoherenceMachineImpl implements CoherenceMachine
{
    private final IGameInfos game;
    private final MessageManager messageManager;
    private final TemplateManager templateManager;

    public CoherenceMachineImpl(IGameInfos game)
    {
        this.game = game;

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
    public IGameInfos getGameInfos()
    {
        return this.game;
    }
}
