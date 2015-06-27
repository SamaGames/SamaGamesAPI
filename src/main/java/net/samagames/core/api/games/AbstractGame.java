package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.IGameManager;
import net.samagames.api.games.IGamePlayer;
import net.samagames.api.games.Status;
import net.samagames.api.games.themachine.ICoherenceMachine;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractGame<GAMEPLAYER extends IGamePlayer>
{
    protected final IGameManager gameManager;

    protected final String gameName;
    protected final Class<GAMEPLAYER> gamePlayerClass;
    protected final HashMap<UUID, GAMEPLAYER> gamePlayers;

    protected ICoherenceMachine coherenceMachine;
    protected Status status;

    public AbstractGame(String gameName, Class<GAMEPLAYER> gamePlayerClass)
    {
        this.gameManager = SamaGamesAPI.get().getGameManager();

        this.gameName = gameName;
        this.gamePlayerClass = gamePlayerClass;
        this.gamePlayers = new HashMap<>();

        this.status = Status.WAITING_FOR_PLAYERS;
    }

    abstract public void startGame();

    public void handlePostRegistration()
    {
        this.coherenceMachine = this.gameManager.getCoherenceMachine();
    }

    public void handleLogin(Player player)
    {
        try
        {
            GAMEPLAYER gamePlayerObject = this.gamePlayerClass.getConstructor(Player.class).newInstance(player);
            gamePlayerObject.handleLogin(false);

            this.gamePlayers.put(player.getUniqueId(), gamePlayerObject);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public void handleModeratorLogin(Player player)
    {
        for(UUID gamePlayerUUID : this.gamePlayers.keySet())
            Bukkit.getPlayer(gamePlayerUUID).hidePlayer(player);

        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.GREEN + "Vous êtes invisibles aux yeux de tous, attention à vos actions !");
    }

    public void handleLogout(Player player)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
        {
            if(!this.gamePlayers.get(player.getUniqueId()).isSpectator())
            {
                if(this.gameManager.isReconnectAllowed())
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerDisconnected(player, this.gameManager.getMaxReconnectTime());
                else
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerQuited(player);
            }

            this.gamePlayers.get(player.getUniqueId()).handleLogout();
        }
    }

    public void handleReconnect(Player player)
    {
        this.gameManager.getCoherenceMachine().getMessageManager().writePlayerReconnected(player);
        this.gamePlayers.get(player.getUniqueId()).handleLogin(true);
    }

    public void handleReconnectTimeOut(Player player)
    {
        this.setSpectator(player);
        this.gameManager.getCoherenceMachine().getMessageManager().writePlayerReconnectTimeOut(player);
    }

    public void handleGameEnd()
    {

    }

    public void setSpectator(Player player)
    {
        this.gamePlayers.get(player.getUniqueId()).setSpectator();
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public String getGameName()
    {
        return this.gameName;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public HashMap<UUID, GAMEPLAYER> getInGamePlayers()
    {
        HashMap<UUID, GAMEPLAYER> temp = new HashMap<>();

        for(UUID key : this.gamePlayers.keySet())
            if(!this.gamePlayers.get(key).isSpectator())
                temp.put(key, this.gamePlayers.get(key));

        return temp;
    }

    public HashMap<UUID, GAMEPLAYER> getSpectatorPlayers()
    {
        HashMap<UUID, GAMEPLAYER> temp = new HashMap<>();

        for(UUID key : this.gamePlayers.keySet())
            if(this.gamePlayers.get(key).isSpectator())
                temp.put(key, this.gamePlayers.get(key));

        return temp;
    }

    public int getConnectedPlayers()
    {
        return this.getInGamePlayers().size();
    }

    public Pair<Boolean, String> canJoinGame(boolean isParty)
    {
        return Pair.of(true, "");
    }
}
