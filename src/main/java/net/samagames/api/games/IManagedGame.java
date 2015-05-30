package net.samagames.api.games;

import org.bukkit.entity.Player;

public abstract class IManagedGame extends IGameInfos
{
    abstract public void startGame();

    abstract public void playerJoin(Player player);
    abstract public void playerDisconnect(Player player);
}
