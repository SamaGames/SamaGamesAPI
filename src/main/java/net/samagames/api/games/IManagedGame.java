package net.samagames.api.games;

import org.bukkit.entity.Player;

public interface IManagedGame extends IGameInfos
{
    void playerJoin(Player player);
    void playerDisconnect(Player player);
}
