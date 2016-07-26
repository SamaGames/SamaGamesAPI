package net.samagames.api.games;

import java.util.UUID;

public interface IGameStatisticsHelper
{
    void increasePlayedTime(UUID uuid, long playedTime);
    void increasePlayedGames(UUID uuid);
    void increaseWins(UUID uuid);
}
