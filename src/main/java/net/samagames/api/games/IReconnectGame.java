package net.samagames.api.games;

import org.bukkit.entity.Player;

public interface IReconnectGame
{
    void playerReconnect(Player player);
    void playerReconnectTimeOut(Player player);
}
