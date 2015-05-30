package net.samagames.api.games;

import org.bukkit.entity.Player;

public abstract class IReconnectGame extends IManagedGame
{
    abstract public void playerReconnect(Player player);
    abstract public void playerReconnectTimeOut(Player player);
}
