package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.IGameManager;
import net.samagames.api.games.IGameProperties;
import net.samagames.api.games.ServerStatus;
import net.samagames.api.games.Status;
import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.core.APIPlugin;
import net.samagames.core.api.games.themachine.CoherenceMachineImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class GameManagerImpl implements IGameManager
{
    private final SamaGamesAPI api;

    private ArrayList<UUID> playersDisconnected;
    private HashMap<UUID, Integer> playerDisconnectTime;
    private HashMap<UUID, BukkitTask> playerReconnectedTimers;
    private IGameProperties gameProperties;
    private AbstractGame game;

    private int maxReconnectTime;

    public GameManagerImpl(SamaGamesAPI api)
    {
        this.api = api;
        this.game = null;

        this.playersDisconnected = new ArrayList<>();
        this.playerDisconnectTime = new HashMap<>();
        this.playerReconnectedTimers = new HashMap<>();

        this.maxReconnectTime = -1;
    }

    @Override
    public void registerGame(AbstractGame game)
    {
        if(this.game != null)
            throw new IllegalStateException("A game is already registered!");

        this.gameProperties = new GameProperties();
        this.game = game;

        APIPlugin.getApi().getJoinManager().registerHandler(new GameLoginHandler(this), 100);

        APIPlugin.getInstance().getExecutor().scheduleAtFixedRate(() ->
        {
            if (game != null) refreshArena();
        }, 5L, 3 * 30L, TimeUnit.SECONDS);

        game.handlePostRegistration();

        APIPlugin.log(Level.INFO, "Registered game '" + game.getGameName() + "' successfuly!");
    }

    @Override
    public void kickPlayer(Player player, String reason)
    {
        player.sendMessage(reason);
        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> player.kickPlayer(null), 10L);
    }

    @Override
    public void onPlayerDisconnect(Player player)
    {
        this.game.handleLogout(player);

        if(!this.isReconnectAllowed())
            return;

        this.playersDisconnected.add(player.getUniqueId());

        this.api.getResource().set("rejoin:" + player.getUniqueId().toString(), this.api.getServerName());
        this.api.getResource().expire("rejoin:" + player.getUniqueId().toString(), this.maxReconnectTime * 60);

        this.playerReconnectedTimers.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimerAsynchronously(APIPlugin.getInstance(), new Runnable() {
            int before = 0;
            int now = 0;
            boolean bool = false;

            @Override
            public void run() {
                if (!this.bool) {
                    if (playerDisconnectTime.containsKey(player.getUniqueId()))
                        this.before = playerDisconnectTime.get(player.getUniqueId());

                    this.bool = true;
                }

                if (this.before == maxReconnectTime * 2 || this.now == maxReconnectTime)
                    onPlayerReconnectTimeOut(player);

                this.before++;
                this.now++;

                playerDisconnectTime.put(player.getUniqueId(), before);
            }
        }, 20L, 20L));

        this.refreshArena();
    }

    @Override
    public void onPlayerReconnect(Player player)
    {
        if(this.playerReconnectedTimers.containsKey(player.getUniqueId()))
        {
            BukkitTask task = this.playerReconnectedTimers.get(player.getUniqueId());

            if(task != null)
                task.cancel();

            this.playerReconnectedTimers.remove(player.getUniqueId());
        }

        this.game.handleReconnect(player);
        this.refreshArena();
    }

    @Override
    public void onPlayerReconnectTimeOut(Player player)
    {
        if(this.playerReconnectedTimers.containsKey(player.getUniqueId()))
        {
            BukkitTask task = this.playerReconnectedTimers.get(player.getUniqueId());

            if(task != null)
                task.cancel();

            this.playerReconnectedTimers.remove(player.getUniqueId());
        }

        this.game.handleReconnectTimeOut(player);
    }

    public void refreshArena()
    {
        if(this.game == null)
            throw new IllegalStateException("Can't refresh arena because the arena is null!");

        new ServerStatus(SamaGamesAPI.get().getServerName(), this.game.getGameName(), this.gameProperties.getMapName(), this.game.getStatus(), this.game.getConnectedPlayers(), this.gameProperties.getMaxSlots()).sendToHubs();
    }

    public void setStatus(Status gameStatus)
    {
        if(this.game == null)
            throw new IllegalStateException("Can't set status of the game because the arena is null!");

        this.game.setStatus(gameStatus);
        this.refreshArena();
    }

    @Override
    public void setMaxReconnectTime(int minutes)
    {
        this.maxReconnectTime = minutes;
    }

    @Override
    public AbstractGame getGame()
    {
        return this.game;
    }

    @Override
    public Status getGameStatus()
    {
        if(this.game == null)
            return null;

        return this.getGame().getStatus();
    }

    @Override
    public ICoherenceMachine getCoherenceMachine()
    {
        if(this.game == null)
            throw new NullPointerException("Can't get CoherenceMachine because game is null!");

        return new CoherenceMachineImpl(this.game, this.gameProperties);
    }

    @Override
    public IGameProperties getGameProperties()
    {
        return this.gameProperties;
    }

    @Override
    public int getMaxReconnectTime()
    {
        return this.maxReconnectTime;
    }

    @Override
    public boolean isWaited(UUID uuid)
    {
        return this.playersDisconnected.contains(uuid);
    }

    @Override
    public boolean isReconnectAllowed()
    {
        return this.maxReconnectTime != -1;
    }
}
