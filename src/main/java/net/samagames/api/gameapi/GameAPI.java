package net.samagames.api.gameapi;

import net.samagames.api.gameapi.themachine.CoherenceMachine;

public interface GameAPI {

    public Game getGame();
    public void initGame(Game game);
    public CoherenceMachine getCoherenceMachine(Game game);
}
