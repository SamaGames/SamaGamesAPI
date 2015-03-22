package net.samagames.api.games;

import net.samagames.api.games.themachine.CoherenceMachine;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GameManager
{
    public void registerGame(IManagedGame game);

    public void onPlayerDisconnect(Player player);
    public void onPlayerReconnect(Player player);
    public void onPlayerReconnectTimeOut(Player player);

    public void allowReconnect(boolean flag);
    public void setMaxReconnectTime(int minutes);

    public IManagedGame getGame();
    public CoherenceMachine getCoherenceMachine();

    public boolean isWaited(UUID uuid);
    public boolean isReconnectAllowed();
}
