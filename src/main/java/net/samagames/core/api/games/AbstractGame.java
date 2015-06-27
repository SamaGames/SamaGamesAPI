package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.IGameManager;
import net.samagames.api.games.Status;
import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.templates.EarningMessageTemplate;
import net.samagames.core.APIPlugin;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractGame<GAMEPLAYER extends GamePlayer>
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

    public void handleLogin(Player player, boolean reconnect)
    {
        try
        {
            GAMEPLAYER gamePlayerObject = this.gamePlayerClass.getConstructor(Player.class).newInstance(player);
            gamePlayerObject.handleLogin(reconnect);

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
        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () ->
        {
            for (UUID playerUUID : this.gamePlayers.keySet()) {
                if (Bukkit.getPlayer(playerUUID) != null) {
                    EarningMessageTemplate earningMessageTemplate = new EarningMessageTemplate();
                    earningMessageTemplate.execute(Bukkit.getPlayer(playerUUID), this.getPlayer(playerUUID).getCoins(), this.getPlayer(playerUUID).getStars());
                }
            }
        }, 20L * 3);

        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () ->
        {
            for (Player player : Bukkit.getOnlinePlayers())
                this.gameManager.kickPlayer(player, null);
        }, 20L * 10);

        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), Bukkit::shutdown, 20L * 11);
    }

    public void addCoins(Player player, int coins, String reason)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.get(player.getUniqueId()).addCoins(coins, reason);
        else
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditCoins(coins, reason, true);
    }

    public void addStars(Player player, int stars, String reason)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.get(player.getUniqueId()).addStars(stars, reason);
        else
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditStars(stars, reason, true);
    }

    public void setSpectator(Player player)
    {
        this.gamePlayers.get(player.getUniqueId()).setSpectator();
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

    public GAMEPLAYER getPlayer(UUID player)
    {
        if(this.gamePlayers.containsKey(player))
            return this.gamePlayers.get(player);
        else
            return null;
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