package net.samagames.api.stats;

import java.util.UUID;

public interface IPlayerStat
{
    boolean fill();
    UUID getPlayerUUID();
    String getGame();
    String getStat();
    long getRank();
    double getValue();
}
