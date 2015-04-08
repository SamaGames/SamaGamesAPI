package net.samagames.api.games;

public interface IGameInfos
{
    int getMaxPlayers();
    int getTotalMaxPlayers();
    int getConnectedPlayers();

    StatusEnum getStatus();

    String getMapName();
    String getGameName();
}
