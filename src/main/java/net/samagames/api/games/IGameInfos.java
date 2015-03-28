package net.samagames.api.games;

public interface IGameInfos
{
    public int getMaxPlayers();
    public int getTotalMaxPlayers();
    public int getConnectedPlayers();

    public StatusEnum getStatus();

    public String getMapName();
    public String getGameName();
}
