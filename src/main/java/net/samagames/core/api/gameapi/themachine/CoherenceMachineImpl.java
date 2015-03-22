package net.samagames.core.api.gameapi.themachine;

import net.samagames.api.gameapi.Game;
import net.samagames.api.gameapi.themachine.CoherenceMachine;
import net.samagames.api.gameapi.themachine.messages.MessageManager;
import net.samagames.api.gameapi.themachine.messages.TemplateManager;
import net.samagames.core.api.gameapi.themachine.messages.MessageManagerImpl;
import net.samagames.core.api.gameapi.themachine.messages.TemplateManagerImpl;
import org.bukkit.ChatColor;

public class CoherenceMachineImpl implements CoherenceMachine
{
    private final Game game;
    private final MessageManager messageManager;
    private final TemplateManager templateManager;

    public CoherenceMachineImpl(Game game)
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
    public Game getGame()
    {
        return this.game;
    }
}
