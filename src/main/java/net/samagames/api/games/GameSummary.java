package net.samagames.api.games;

import com.google.gson.JsonObject;

public abstract class GameSummary<GAME extends Game>
{
    private final GAME game;

    public GameSummary(GAME game)
    {
        this.game = game;
    }

    public abstract JsonObject make();
}
