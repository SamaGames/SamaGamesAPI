package net.samagames.core.api.games.themachine.messages;

import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.TemplateManager;
import net.samagames.api.games.themachine.messages.templates.BasicMessageTemplate;
import net.samagames.api.games.themachine.messages.templates.CustomWinTemplate;
import net.samagames.api.games.themachine.messages.templates.PlayerLeaderboardWinTemplate;
import net.samagames.api.games.themachine.messages.templates.PlayerWinTemplate;

public class TemplateManagerImpl implements TemplateManager
{
    private final ICoherenceMachine machine;

    public TemplateManagerImpl(ICoherenceMachine machine)
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
