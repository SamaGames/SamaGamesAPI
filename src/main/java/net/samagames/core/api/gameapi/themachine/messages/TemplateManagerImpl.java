package net.samagames.core.api.gameapi.themachine.messages;

import net.samagames.api.gameapi.themachine.CoherenceMachine;
import net.samagames.api.gameapi.themachine.messages.TemplateManager;
import net.samagames.api.gameapi.themachine.messages.templates.BasicMessageTemplate;
import net.samagames.api.gameapi.themachine.messages.templates.CustomWinTemplate;
import net.samagames.api.gameapi.themachine.messages.templates.PlayerLeaderboardWinTemplate;
import net.samagames.api.gameapi.themachine.messages.templates.PlayerWinTemplate;

public class TemplateManagerImpl implements TemplateManager
{
    private final CoherenceMachine machine;

    public TemplateManagerImpl(CoherenceMachine machine)
    {
        this.machine = machine;
    }

    @Override
    public BasicMessageTemplate getBasicMessageTemplate()
    {
        return new BasicMessageTemplate();
    }

    @Override
    public CustomWinTemplate getCustomWinTemplate()
    {
        return new CustomWinTemplate();
    }

    @Override
    public PlayerWinTemplate getPlayerWinTemplate()
    {
        return new PlayerWinTemplate();
    }

    @Override
    public PlayerLeaderboardWinTemplate getPlayerLeaderboardWinTemplate()
    {
        return new PlayerLeaderboardWinTemplate();
    }
}
