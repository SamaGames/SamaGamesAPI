package net.samagames.api.games;

import com.google.gson.Gson;
import net.samagames.api.SamaGamesAPI;

public class ServerStatus
{
    private String game;
    private String map;
    private int players;
    private int maxPlayers;

    public ServerStatus(String game, String map, int players, int maxPlayers)
    {
        this.setGame(game);
        this.setMap(map);
        this.setPlayers(players);
        this.setMaxPlayers(maxPlayers);
    }

    public void sendToHubs()
    {
        String json = new Gson().toJson(this);
        SamaGamesAPI.get().getPubSub().send("hubsChannel", json);
    }

    public void setGame(String game)
    {
        this.game = game;
    }

    public void setMap(String map)
    {
        this.map = map;
    }

    public void setPlayers(int players)
    {
        this.players = players;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public String getGame()
    {
        return game;
    }

    public String getMap()
    {
        return map;
    }

    public int getPlayers()
    {
        return players;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }
}
