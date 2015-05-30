package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;

public abstract class IGameInfos
{
    Status status = Status.STARTING;

    abstract public int getMaxPlayers();
    abstract public int getConnectedPlayers();

    abstract public Status getStatus();

    public void setStatus(Status status){
         this.status = status;
        refreshArena();
    }

    public void refreshArena()
    {
        SamaGamesAPI.get().getGameManager().refreshArena();
    }

    abstract public String getMapName();
    abstract public String getGameName();
}
