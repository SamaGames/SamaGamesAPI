package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.templates.EarningMessageTemplate;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Game<GAMEPLAYER extends GamePlayer>
{
    protected final IGameManager gameManager;

    protected final String gameCodeName;
    protected final String gameName;
    protected final Class<GAMEPLAYER> gamePlayerClass;
    protected final HashMap<UUID, GAMEPLAYER> gamePlayers;
    protected final BukkitTask beginTimer;

    protected ICoherenceMachine coherenceMachine;
    protected Status status;

    public Game(String gameCodeName, String gameName, Class<GAMEPLAYER> gamePlayerClass)
    {
        this.gameManager = SamaGamesAPI.get().getGameManager();

        this.gameCodeName = gameCodeName;
        this.gameName = gameName;
        this.gamePlayerClass = gamePlayerClass;
        this.gamePlayers = new HashMap<>();
        this.beginTimer = Bukkit.getScheduler().runTaskTimerAsynchronously(SamaGamesAPI.get().getPlugin(), new BeginTimer(this), 20L, 20L);

        this.setStatus(Status.WAITING_FOR_PLAYERS);
    }

    public void startGame()
    {
        this.beginTimer.cancel();
        this.setStatus(Status.IN_GAME);

        this.coherenceMachine.getMessageManager().writeGameStart();
    }

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

        this.coherenceMachine.getMessageManager().writePlayerJoinToAll(player);
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
                if(this.gameManager.isReconnectAllowed() && this.status == Status.IN_GAME)
                {
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerDisconnected(player, this.gameManager.getMaxReconnectTime());
                }
                else
                {
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerQuited(player);
                    this.getPlayer(player.getUniqueId()).setSpectator();
                }
            }

            this.gamePlayers.get(player.getUniqueId()).handleLogout();

            if(this.status != Status.IN_GAME)
                this.gamePlayers.remove(player.getUniqueId());
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
        Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
        {
            for (UUID playerUUID : this.gamePlayers.keySet())
            {
                if (Bukkit.getPlayer(playerUUID) != null)
                {
                    EarningMessageTemplate earningMessageTemplate = this.coherenceMachine.getTemplateManager().getEarningMessageTemplate();
                    earningMessageTemplate.execute(Bukkit.getPlayer(playerUUID), this.getPlayer(playerUUID).getCoins(), this.getPlayer(playerUUID).getStars());
                }
            }
        }, 20L * 3);

        Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
        {
            for (Player player : Bukkit.getOnlinePlayers())
                this.gameManager.kickPlayer(player, null);
        }, 20L * 10);

        Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), Bukkit::shutdown, 20L * 11);
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

    public void increaseStat(UUID uuid, String statName, int count)
    {
        SamaGamesAPI.get().getStatsManager(this.gameCodeName).increase(uuid, statName, count);
    }

    public void setSpectator(Player player)
    {
        this.gamePlayers.get(player.getUniqueId()).setSpectator();
    }

    public void setStatus(Status status)
    {
        this.status = status;
        this.gameManager.refreshArena();
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

    /**
     * Savoir si un joueur est en jeu ou non
     *
     * @param player Le joueur en question
     * @return Oui ou non
     */
    public boolean hasPlayer(Player player)
    {
        return this.getInGamePlayers().containsKey(player.getUniqueId());
    }

    /**
     * Savoir si un joueur peut-entrer en jeu
     *
     * @param player Le joueur en question
     * @param reconnect Si le joueur se reconnecte pendant le jeu ou non
     * @return Un objet Pair de la forme <Autorisé ? (Oui/Non), Raison du refus (Si refus)>
     */
    public Pair<Boolean, String> canJoinGame(UUID player, boolean reconnect)
    {
        return Pair.of(true, "");
    }

    /**
     * Savoir si une partie peut entrer entière en jeu
     *
     * @param partyMembers Les joueurs de la partie
     * @return Un objet Pair de la forme <Autorisé ? (Oui/Non), Raison du refus (Si refus)>
     */
    public Pair<Boolean, String> canPartyJoinGame(Set<UUID> partyMembers)
    {
        return Pair.of(true, "");
    }

    /**
     * Savoir si un joueur est un spectateur
     *
     * @param player Le joueur en question
     * @return Oui ou non
     */
    public boolean isSpectator(Player player)
    {
        return this.getSpectatorPlayers().containsKey(player.getUniqueId());
    }
}