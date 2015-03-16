package net.samagames.core.api.gameapi;

import net.samagames.api.gameapi.Game;
import net.samagames.api.gameapi.GameAPI;
import net.samagames.core.APIPlugin;
import net.samagames.core.ApiImplementation;

public class GameAPIImplement implements GameAPI {

    private Game game;
    private GameLoginHandler handler;
    private final ApiImplementation implementation;

    public GameAPIImplement(ApiImplementation implementation) {
        this.implementation = implementation;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void initGame(Game game) {
        if (this.game != null)
            throw new IllegalStateException("Game is already initialized. Cannot init it twice.");

        this.game = game;
        this.handler = new GameLoginHandler(this);
        implementation.getJoinManager().registerHandler(handler, 100);
        APIPlugin.getInstance().getLogger().info("Loaded game and GameLoginHandler loaded.");
    }
}
