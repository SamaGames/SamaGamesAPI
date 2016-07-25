package net.samagames.api.games;

import java.util.UUID;

public interface IGameStatisticsHelper
{
    void setPlayedTime(UUID uuid, long playedTime);
    void setPlayedGames(UUID uuid, int playedGames);
    void setWins(UUID uuid, int wins);

    long getPlayedTime(UUID uuid);
    int getPlayedGames(UUID uuid);
    int getWins(UUID uuid);
}
