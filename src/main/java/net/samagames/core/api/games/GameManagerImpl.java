package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.*;
import net.samagames.api.games.themachine.CoherenceMachine;
import net.samagames.core.APIPlugin;
import net.samagames.core.api.games.themachine.CoherenceMachineImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class GameManagerImpl implements GameManager
{
    private final SamaGamesAPI api;

    private ArrayList<UUID> playersDisconnected;
    private HashMap<UUID, Integer> playerDisconnectTime;
    private HashMap<UUID, Integer> playerReconnectedTimers;
    private IManagedGame game;
    private Status gameStatus;

    private boolean allowReconnect;
    private int maxReconnectTime;

    public GameManagerImpl(SamaGamesAPI api)
    {
        this.api = api;
        this.game = null;

        this.playersDisconnected = new ArrayList<>();
        this.playerDisconnectTime = new HashMap<>();
        this.playerReconnectedTimers = new HashMap<>();
    }

    @Override
    public void registerGame(IManagedGame game)
    {
        if(this.game != null)
            throw new IllegalStateException("A game is already registered!");

        this.game = game;
        APIPlugin.getApi().getJoinManager().registerHandler(new GameLoginHandler(this), 100);

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
        this.game.playerDisconnect(player);

        if(this.game instanceof IReconnectGame)
            return;

        if(this.allowReconnect)
        {
            this.playersDisconnected.add(player.getUniqueId());

            this.api.getResource().set("rejoin:" + player.getUniqueId().toString(), this.api.getServerName());
            this.api.getResource().expire("rejoin:" + player.getUniqueId().toString(), this.maxReconnectTime * 60);

            this.playerReconnectedTimers.put(player.getUniqueId(), Bukkit.getScheduler().scheduleAsyncRepeatingTask(APIPlugin.getInstance(), new Runnable() {
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

                    if (this.before == maxReconnectTime * 2 || this.now == maxReconnectTime) {
                        onPlayerReconnectTimeOut(player);
                    }

                    this.before++;
                    this.now++;
                    playerDisconnectTime.put(player.getUniqueId(), before);
                }
            }, 20L, 20L));
        }
    }

    @Override
    public void onPlayerReconnect(Player player)
    {
        if(!(this.game instanceof IReconnectGame))
            return;

        if(this.playerReconnectedTimers.containsKey(player.getUniqueId()))
        {
            Bukkit.getScheduler().cancelTask(this.playerReconnectedTimers.get(player.getUniqueId()));
            this.playerReconnectedTimers.remove(player.getUniqueId());
        }

        ((IReconnectGame) this.game).playerReconnect(player);
    }

    @Override
    public void onPlayerReconnectTimeOut(Player player)
    {
        if(!(this.game instanceof IReconnectGame))
            return;

        if(this.playerReconnectedTimers.containsKey(player.getUniqueId()))
        {
            Bukkit.getScheduler().cancelTask(this.playerReconnectedTimers.get(player.getUniqueId()));
            this.playerReconnectedTimers.remove(player.getUniqueId());
        }

        ((IReconnectGame) this.game).playerReconnectTimeOut(player);
    }

    public void refreshArena()
    {
        if(this.game == null)
            throw new IllegalStateException("Can't refresh arena because the arena is null!");

        new ServerStatus(SamaGamesAPI.get().getServerName(), this.game.getGameName(), this.game.getMapName(), this.gameStatus, this.game.getConnectedPlayers(), this.game.getMaxPlayers()).sendToHubs();
    }

    public void setStatus(Status gameStatus)
    {
        if(this.game == null)
            throw new IllegalStateException("Can't set status of the game because the arena is null!");

        this.gameStatus = gameStatus;
        this.refreshArena();
    }

    @Override
    public void allowReconnect(boolean flag)
    {
        this.allowReconnect = flag;
    }

    @Override
    public void setMaxReconnectTime(int minutes)
    {
        this.maxReconnectTime = minutes;
    }

    @Override
    public IManagedGame getGame()
    {
        return this.game;
    }

    @Override
    public CoherenceMachine getCoherenceMachine()
    {
        if(this.game == null)
            throw new NullPointerException("Can't get CoherenceMachine because game is null!");

        return new CoherenceMachineImpl(this.game);
    }

    @Override
    public boolean isWaited(UUID uuid)
    {
        return this.playersDisconnected.contains(uuid);
    }

    @Override
    public boolean isReconnectAllowed()
    {
        return this.allowReconnect;
    }
}
