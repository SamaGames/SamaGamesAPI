package net.samagames.api.games;

import net.samagames.api.games.themachine.CoherenceMachine;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GameManager
{
    public void registerGame(IManagedGame game);

    void onPlayerDisconnect(Player player);
    void onPlayerReconnect(Player player);
    void onPlayerReconnectTimeOut(Player player);

    void allowReconnect(boolean flag);
    void setMaxReconnectTime(int minutes);

    IManagedGame getGame();
    CoherenceMachine getCoherenceMachine();

    boolean isWaited(UUID uuid);
    boolean isReconnectAllowed();
}
