package net.samagames.api.games;

public interface IGameInfos
{
    int getMaxPlayers();
    int getConnectedPlayers();

    Status getStatus();

    void setStatus(Status status);

    String getMapName();
    String getGameName();
}
