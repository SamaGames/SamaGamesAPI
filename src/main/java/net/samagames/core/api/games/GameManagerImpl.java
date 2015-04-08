package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.GameManager;
import net.samagames.api.games.IManagedGame;
import net.samagames.api.games.IReconnectGame;
import net.samagames.api.games.ReconnectHandler;
import net.samagames.api.games.themachine.CoherenceMachine;
import net.samagames.core.APIPlugin;
import net.samagames.core.api.games.themachine.CoherenceMachineImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class GameManagerImpl implements GameManager
{
    private final SamaGamesAPI api;
    private IManagedGame game;
    private ReconnectHandler reconnectHandler;

    public GameManagerImpl(SamaGamesAPI api) {
        this.api = api;
        this.game = null;
    }

    @Override
    public void registerGame(IManagedGame game)
    {
        if(this.game != null)
            throw new IllegalStateException("A game is already registered!");

        this.game = game;
        APIPlugin.getApi().getJoinManager().registerHandler(new GameLoginHandler(this), 100);

        APIPlugin.log(Level.INFO, "Registered game '" + game.getGameName() + "' successfuly!");
    }

    @Override
    public ReconnectHandler getReconnectHandler() {
        return reconnectHandler;
    }

    @Override
    public void setReconnectHandler(ReconnectHandler reconnectHandler) {
        this.reconnectHandler = reconnectHandler;
        reconnectHandler.asign((IReconnectGame) game);
    }

    @Override
    public IManagedGame getGame() {
        return this.game;
    }

    @Override
    public CoherenceMachine getCoherenceMachine() {
        if(this.game == null)
            throw new NullPointerException("Can't get CoherenceMachine because game is null!");

        return new CoherenceMachineImpl(this.game);
    }
}
