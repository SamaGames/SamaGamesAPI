package net.samagames.api.games;

import org.bukkit.entity.Player;

public interface IManagedGame extends IGameInfos
{
    public void playerJoin(Player player);

    public void playerDisconnect(Player player);
    public void playerReconnect(Player player);
    public void playerReconnectTimeOut(Player player);
}
