package net.samagames.api.games;

import net.samagames.api.games.themachine.CoherenceMachine;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GameManager {
    public void registerGame(IManagedGame game);
    ReconnectHandler getReconnectHandler();
    void setReconnectHandler(ReconnectHandler reconnectHandler);
    IManagedGame getGame();
    CoherenceMachine getCoherenceMachine();

}
