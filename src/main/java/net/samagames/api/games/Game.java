package net.samagames.api.games;

import in.ashwanthkumar.slack.webhook.SlackMessage;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.pearls.Pearl;
import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.templates.EarningMessageTemplate;
import net.samagames.tools.Titles;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Game<GAMEPLAYER extends GamePlayer>
{
    protected final IGameManager gameManager;

    protected final String gameCodeName;
    protected final String gameName;
    protected final String gameDescription;
    protected final Class<GAMEPLAYER> gamePlayerClass;
    protected final List<UUID> gameCreators;
    protected final List<UUID> gameWinners;
    protected final List<UUID> gameModerators;
    protected final HashMap<UUID, GAMEPLAYER> gamePlayers;
    protected final HashMap<UUID, GAMEPLAYER> gameSpectators;
    protected final AdvertisingTask advertisingTask;
    protected BukkitTask beginTimer;
    protected BeginTimer beginObj;

    protected ICoherenceMachine coherenceMachine;
    protected Status status;
    protected long startTime = -1;

    /**
     * @param gameCodeName The code name of the game, given by an administrator.
     * @param gameName The friendly name of the game.
     * @param gameDescription A short description of the game, displayed to the players
     *                        when they join the game through a /title.
     * @param gamePlayerClass The class of your custom {@link GamePlayer} object, the same
     *                        as the {@link GAMEPLAYER} class. Use {@code GamePlayer.class}
     *                        if you are not using a custom class.
     * @param gameCreators An array of the UUID of the creators of this game (used for
     *                     the achievement 'Play with the creator').
     */
    public Game(String gameCodeName, String gameName, String gameDescription, Class<GAMEPLAYER> gamePlayerClass, UUID[] gameCreators)
    {
        this.gameManager = SamaGamesAPI.get().getGameManager();
        this.gameCodeName = gameCodeName.toLowerCase();
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gamePlayerClass = gamePlayerClass;
        this.gameCreators = gameCreators != null ? Arrays.asList(gameCreators) : null;
        this.gameWinners = new ArrayList<>();
        this.gameModerators = new ArrayList<>();
        this.gamePlayers = new HashMap<>();
        this.gameSpectators = new HashMap<>();
        this.advertisingTask = new AdvertisingTask();

        this.status = Status.WAITING_FOR_PLAYERS;
    }

    /**
     * @param gameCodeName The code name of the game, given by an administrator.
     * @param gameName The friendly name of the game.
     * @param gameDescription A short description of the game, displayed to the players
     *                        when they join the game through a /title.
     * @param gamePlayerClass The class of your custom {@link GamePlayer} object, the same
     *                        as the {@link GAMEPLAYER} class. Use {@code GamePlayer.class}
     *                        if you are not using a custom class.
     */
    public Game(String gameCodeName, String gameName, String gameDescription, Class<GAMEPLAYER> gamePlayerClass)
    {
        this(gameCodeName, gameName, gameDescription, gamePlayerClass, null);
    }

    /**
     * Starts the game.
     *
     * Override this command to execute something when the game starts.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     */
    public void startGame()
    {
        if (this.gameManager.isFreeMode())
            throw new UnsupportedOperationException("You can't use this method while using the free mode!");

        //Network hook don't touch
        this.gameManager.startTimer();

        this.startTime = System.currentTimeMillis();
        this.beginTimer.cancel();
        this.setStatus(Status.IN_GAME);

        this.gamePlayers.values().forEach((gamePlayer) ->
        {
            gamePlayer.initPlayedTimeCounter();
            gamePlayer.getPlayerIfOnline().setLevel(0);
            gamePlayer.getPlayerIfOnline().setExp(0.0F);
        });

        if (this.gameManager.getGameStatisticsHelper() != null)
            for (UUID uuid : this.gamePlayers.keySet())
                this.gameManager.getGameStatisticsHelper().increasePlayedGames(uuid);

        this.coherenceMachine.getMessageManager().writeGameStart();

    }

    /**
     * Override this method to execute something when the game was just registered.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     */
    public void handlePostRegistration()
    {
        this.coherenceMachine = this.gameManager.getCoherenceMachine();
        this.beginObj = new BeginTimer(this);

        if (!this.gameManager.isFreeMode())
            this.beginTimer = Bukkit.getScheduler().runTaskTimerAsynchronously(SamaGamesAPI.get().getPlugin(), this.beginObj, 20L, 20L);

        if (this.gameManager.getGameStatisticsHelper() == null)
            Bukkit.getLogger().severe("NO STATISTICS HELPER REGISTERED, PLAYERS WILL LOST THEIR STATISTICS DURING THIS GAME.");
    }

    /**
     * Override this to execute something when a normal player joins the game at the
     * beginning of it (this will neo be called for reconnections).
     * Prefer the use of {@link GamePlayer#handleLogin(boolean)}.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     *
     * @param player The player who logged in.
     */
    public void handleLogin(Player player)
    {
        try
        {
            GAMEPLAYER gamePlayerObject = this.gamePlayerClass.getConstructor(Player.class).newInstance(player);
            gamePlayerObject.handleLogin(false);

            this.gamePlayers.put(player.getUniqueId(), gamePlayerObject);

            Titles.sendTitle(player, 20, 20 * 3, 20, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + this.gameName, ChatColor.AQUA + this.gameDescription);
            this.advertisingTask.addPlayer(player);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
            SamaGamesAPI.get().slackLog(Level.SEVERE, new SlackMessage("[" + SamaGamesAPI.get().getServerName() + "] Failed to handle '" + player.getName() + "'s login: " + e.getMessage()));
        }

        this.coherenceMachine.getMessageManager().writePlayerJoinToAll(player, !this.gameManager.isFreeMode());
    }

    /**
     * Override this to execute something when a moderator joins the game.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     *
     * @param player The player who logged in.
     */
    public void handleModeratorLogin(Player player)
    {
        for (GamePlayer gamePlayer : this.gamePlayers.values())
        {
            Player p = gamePlayer.getPlayerIfOnline();

            if (p != null)
                p.hidePlayer(player);
        }

        this.gameModerators.add(player.getUniqueId());

        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.GREEN + "Vous êtes invisibles aux yeux de tous, attention à vos actions !");
    }

    /**
     * Override this to execute something when a player logout at any time.
     * Prefer the use of {@link GamePlayer#handleLogout()}.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     *
     * @param player The player who logged out.
     */
    public void handleLogout(Player player)
    {
        String key = "lastgame:" + player.getPlayer().toString();

        Jedis jedis = SamaGamesAPI.get().getBungeeResource();

        if (jedis != null)
        {
            jedis.set(key, this.gameCodeName);
            jedis.expire(key, 60 * 3);
            jedis.close();
        }

        if (this.status == Status.FINISHED)
            return;

        if (this.gamePlayers.containsKey(player.getUniqueId()))
        {
            if (!this.gamePlayers.get(player.getUniqueId()).isSpectator())
            {
                if (this.gameManager.isReconnectAllowed(player) && this.status == Status.IN_GAME)
                {
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerDisconnected(player, this.gameManager.getMaxReconnectTime() * 60000);
                }
                else
                {
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerQuited(player);
                }
            }

            this.gamePlayers.get(player.getUniqueId()).handleLogout();
            this.advertisingTask.removePlayer(player);

            if (this.status != Status.IN_GAME || !this.gameManager.isReconnectAllowed(player))
                this.gamePlayers.remove(player.getUniqueId());

            this.gameManager.refreshArena();
        }
        else if (this.gameModerators.contains(player.getUniqueId()))
        {
            this.gameModerators.remove(player.getUniqueId());
        }
    }

    /**
     * Override this to execute something when a player reconnects into the game.
     * Prefer the use of {@link GamePlayer#handleLogin(boolean)}.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     *
     * @param player The player who just logged in back.
     */
    public void handleReconnect(Player player)
    {
        if (this.gameManager.isFreeMode())
            throw new UnsupportedOperationException("You can't use this method while using the free mode!");

        GamePlayer gamePlayer = this.gamePlayers.get(player.getUniqueId());

        if (gamePlayer == null)
        {
            this.handleReconnectTimeOut(player, true);
            return;
        }

        this.gameManager.getCoherenceMachine().getMessageManager().writePlayerReconnected(player);

        if (gamePlayer.isSpectator() && !this.gameModerators.contains(gamePlayer.getUUID()))
            gamePlayer.setSpectator();
        else
            gamePlayer.handleLogin(true);
    }

    /**
     * Override this to execute something when a disconnected player can no longer join
     * the game.
     *
     * You need to call the {@code super} method at the beginning of your own one.
     *
     * @param player The player who can no longer rejoin the game.
     * @param silent Display a message
     */
    public void handleReconnectTimeOut(OfflinePlayer player, boolean silent)
    {
        if (this.gameManager.isFreeMode())
            throw new UnsupportedOperationException("You can't use this method while using the free mode!");

        if (this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.remove(player.getUniqueId());

        this.gameManager.refreshArena();

        if (!silent)
            this.gameManager.getCoherenceMachine().getMessageManager().writePlayerReconnectTimeOut(player);
    }

    /**
     * Call this method to modify the statistics of the
     * given winner.
     *
     * @param uuid UUID of the winner
     */
    public void handleWinner(UUID uuid)
    {
        if (this.gameManager.isFreeMode())
            throw new UnsupportedOperationException("You can't use this method while using the free mode!");

        try
        {
            this.gameWinners.add(uuid);

            if (this.gameManager.getGameStatisticsHelper() != null)
                this.gameManager.getGameStatisticsHelper().increaseWins(uuid);

            SamaGamesAPI.get().getAchievementManager().getAchievementByID(25).unlock(uuid);
            Arrays.asList(26, 27, 28, 29).forEach(id -> SamaGamesAPI.get().getAchievementManager().incrementAchievement(uuid, id, 1));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            SamaGamesAPI.get().slackLog(Level.SEVERE, new SlackMessage("[" + SamaGamesAPI.get().getServerName() + "] Failed to handle '" + SamaGamesAPI.get().getUUIDTranslator().getName(uuid) + "'s win: " + e.getMessage()));
        }
    }

    /**
     * Call this method when the game is finished.
     *
     * If for some reasons you want to override this method, you will need to call the
     * {@code super} method at the beginning of your own one.
     */
    public void handleGameEnd()
    {
        if (this.gameManager.isFreeMode())
            throw new UnsupportedOperationException("You can't use this method while using the free mode!");

        this.setStatus(Status.FINISHED);

        // Network hook don't touch
        this.gameManager.stopTimer();
        this.getInGamePlayers().values().forEach(GamePlayer::stepPlayedTimeCounter);

        for (GamePlayer player : this.getRegisteredGamePlayers().values())
        {
            try
            {
                if (this.gameManager.getGameStatisticsHelper() != null)
                    this.gameManager.getGameStatisticsHelper().increasePlayedTime(player.getUUID(), player.getPlayedTime());
            }
            catch (Exception ignored) {}
        }

        boolean wasAStaffMember = false;
        boolean wasAGameCreator = false;
        boolean wasACoupaingInGame = false;
        boolean wasASamAllieInGame = false;
        boolean wasAnHidden = false;

        for (GamePlayer player : this.gamePlayers.values())
        {
            if (SamaGamesAPI.get().getPermissionsManager().hasPermission(player.getUUID(), "network.staff"))
            {
                wasAStaffMember = true;

                if (this.gameCreators != null && this.gameCreators.contains(player.getUUID()))
                    wasAGameCreator = true;
            }
            else if (SamaGamesAPI.get().getPermissionsManager().getPlayer(player.getUUID()).getGroupId() == 4)
            {
                wasACoupaingInGame = true;

                if (SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUUID()).hasNickname())
                    wasAnHidden = true;
            }
            else if (SamaGamesAPI.get().getPermissionsManager().getPlayer(player.getUUID()).getGroupId() == 5)
            {
                wasASamAllieInGame = true;

                if (SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUUID()).hasNickname())
                    wasAnHidden = true;
            }
        }

        for (GamePlayer player : this.gamePlayers.values())
        {
            if (player.isOnline())
            {
                boolean finalWasAStaffMember = wasAStaffMember;
                boolean finalWasAGameCreator = wasAGameCreator;
                boolean finalWasACoupaingInGame = wasACoupaingInGame;
                boolean finalWasASamAllieInGame = wasASamAllieInGame;
                boolean finalWasAnHidden = wasAnHidden;

                Bukkit.getScheduler().runTask(SamaGamesAPI.get().getPlugin(), () ->
                {
                   if (finalWasAStaffMember)
                       SamaGamesAPI.get().getAchievementManager().getAchievementByID(15).unlock(player.getUUID());

                    if (finalWasAGameCreator)
                        SamaGamesAPI.get().getAchievementManager().getAchievementByID(16).unlock(player.getUUID());

                    if (finalWasACoupaingInGame)
                        SamaGamesAPI.get().getAchievementManager().getAchievementByID(13).unlock(player.getUUID());

                    if (finalWasASamAllieInGame)
                        SamaGamesAPI.get().getAchievementManager().getAchievementByID(14).unlock(player.getUUID());

                    if (finalWasAnHidden)
                        SamaGamesAPI.get().getAchievementManager().getAchievementByID(17).unlock(player.getUUID());

                    Arrays.asList(30, 31, 32, 33, 34).forEach(id -> SamaGamesAPI.get().getAchievementManager().incrementAchievement(player.getUUID(), id, player.getCoins()));
                });
            }
        }

        Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
        {
            this.gamePlayers.keySet().stream().filter(playerUUID -> Bukkit.getPlayer(playerUUID) != null).forEach(playerUUID ->
            {
                Pearl pearl = this.gameManager.getPearlManager().runGiveAlgorythm(Bukkit.getPlayer(playerUUID), (int) TimeUnit.MILLISECONDS.toSeconds(this.gameManager.getGameTime()), this.gameWinners.contains(playerUUID));

                EarningMessageTemplate earningMessageTemplate = this.coherenceMachine.getTemplateManager().getEarningMessageTemplate();
                earningMessageTemplate.execute(Bukkit.getPlayer(playerUUID), this.getPlayer(playerUUID).getCoins(), pearl);
            });
        }, 20L * 3);

        Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
        {
            for (Player player : Bukkit.getOnlinePlayers())
                this.gameManager.kickPlayer(player, null);
        }, 20L * 10);

        Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
        {
            SamaGamesAPI.get().getStatsManager().finish();
            Bukkit.shutdown();
        }, 20L * 15);
    }

    /**
     * Create fireworks to a given player.
     *
     * @param player Player who will receive the fireworks
     */
    public void effectsOnWinner(Player player)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SamaGamesAPI.get().getPlugin(), new WinEffect(player), 5L, 5L);
    }

    /**
     * Credits coins to the given player. Works for offline players.
     *
     * Use {@link GamePlayer#addCoins(int, String)} instead, if possible.
     *
     * @param player The receiver of the coins.
     * @param coins The amount of coins.
     * @param reason The displayed reason of this credit.
     */
    public void addCoins(Player player, int coins, String reason)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.get(player.getUniqueId()).addCoins(coins, reason);
        else
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditCoins(coins, reason, true);
    }

    /**
     * Marks a player as spectator.
     *
     * @param player The player to mark as spectator.
     *
     * @see GamePlayer#setSpectator() The method of the GamePlayer object (to be used if possible).
     */
    public void setSpectator(Player player)
    {
        if (player == null)
            return;

        if (this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.get(player.getUniqueId()).setSpectator();
    }

    /**
     * Returns this game's code name.
     *
     * @return The code.
     */
    public String getGameCodeName()
    {
        return this.gameCodeName;
    }

    /**
     * Returns this game's name.
     *
     * @return The name.
     */
    public String getGameName()
    {
        return this.gameName;
    }

    /**
     * Returns this game's current status.
     *
     * @return The status.
     */
    public Status getStatus()
    {
        return this.status;
    }

    /**
     * Returns the CoherenceMachine instance
     *
     * @return The instance
     */
    public ICoherenceMachine getCoherenceMachine()
    {
        return this.coherenceMachine;
    }

    /**
     * Set the game's status. You'll never have to use this normally.
     *
     * @param status The new status.
     */
    public void setStatus(Status status)
    {
        this.status = status;
        this.gameManager.refreshArena();
    }

    /**
     * Returns the internal representation of the given player.
     *
     * @param player The player's UUID.
     * @return The instance of {@link GAMEPLAYER} representing this player.
     */
    public GAMEPLAYER getPlayer(UUID player)
    {
        if(this.gamePlayers.containsKey(player))
            return this.gamePlayers.get(player);
        else
            return null;
    }

    /**
     * Return a map ({@link UUID} → {@link GamePlayer}) of the currently in-game
     * players (i.e. players neither dead nor moderators).
     *
     * This map contains offline players who are still able to login, if the reconnection is
     * allowed.
     *
     * @return The map containing the in-game players.
     */
    public Map<UUID, GAMEPLAYER> getInGamePlayers()
    {
        Map<UUID, GAMEPLAYER> inGamePlayers = new HashMap<>();

        for(UUID key : this.gamePlayers.keySet())
        {
            final GAMEPLAYER gPlayer = this.gamePlayers.get(key);

            if (!gPlayer.isSpectator())
                inGamePlayers.put(key, gPlayer);
        }

        return inGamePlayers;
    }

    /**
     * Return a map ({@link UUID} → {@link GamePlayer}) of the currently spectating players.
     *
     * This map does not contains offline players who are still able to login, if the
     * reconnection is allowed.
     *
     * @return The map containing the spectating players.
     */
    public Map<UUID, GAMEPLAYER> getSpectatorPlayers()
    {
        Map<UUID, GAMEPLAYER> spectators = new HashMap<>();

        for(UUID key : this.gamePlayers.keySet())
        {
            final GAMEPLAYER gPlayer = this.gamePlayers.get(key);

            if(gPlayer.isSpectator())
                spectators.put(key, gPlayer);
        }

        return spectators;
    }

    /**
     * Return a map ({@link UUID} → {@link GamePlayer}) of the currently spectating players.
     *
     * This map does not contains offline players who are still able to login, if the
     * reconnection is allowed.
     *
     * @return The map containing the spectating players.
     */
    public Map<UUID, GAMEPLAYER> getVisibleSpectatingPlayers()
    {
        Map<UUID, GAMEPLAYER> spectators = new HashMap<>();

        for (UUID key : this.gamePlayers.keySet())
        {
            GAMEPLAYER gamePlayer = this.gamePlayers.get(key);

            if (gamePlayer.isSpectator() && !this.gameModerators.contains(gamePlayer.getUUID()))
                spectators.put(key, gamePlayer);
        }

        return spectators;
    }

    /**
     * Return a read-only map ({@link UUID} → {@link GamePlayer}) of the registered players.
     *
     * @return All registered game players.
     */
    public Map<UUID, GAMEPLAYER> getRegisteredGamePlayers()
    {
        return Collections.unmodifiableMap(this.gamePlayers);
    }

    /**
     * Returns the timer used to count down the time when the game is not started.
     *
     * @return The timer.
     */
    public BukkitTask getBeginTimer()
    {
        return this.beginTimer;
    }

    /**
     * Returns the amount of in-game (alive) players.
     *
     * Calling this is mostly the same as calling {@code getInGamePlayers().size()}, but
     * with better performances.
     *
     * @return The amount of in-game (alive) players.
     */
    public int getConnectedPlayers()
    {
        int i = 0;

        for (GamePlayer player : this.gamePlayers.values())
            if (!player.isSpectator())
                i++;

        return i;
    }

    /**
     * Checks if the specified player is registered and have a stored {@link GAMEPLAYER}
     * representation.
     *
     * @param player The player.
     * @return {@code true} if registered.
     */
    public boolean hasPlayer(Player player)
    {
        return this.gamePlayers.containsKey(player.getUniqueId());
    }

    /**
     * Called when a player try to login into the game, this will check if the player
     * is able to join or not.
     *
     * The default implementation allows anyone to join or rejoin (if the reconnection is
     * allowed); override this to change this behavior.
     *
     * @param player The player trying to login.
     * @param reconnect {@code true} if the player is reconnecting.
     *
     * @return A {@link Pair} instance, where:
     * <ul>
     *     <li>
     *         the left value is a {@link Boolean}, saying if the player is allowed to join
     *         ({@code true}) or not ({@code false});
     *     </li>
     *     <li>
     *         the right value is the error message displayed, if the connection
     *         is refused; ignored else.
     *     </li>
     * </ul>
     */
    public Pair<Boolean, String> canJoinGame(UUID player, boolean reconnect)
    {
        return Pair.of(true, "");
    }

    /**
     * Called when a party try to join the server; this will check if the whole party can login
     * or not.
     *
     * The default implementation allows any party to join (assuming the server is large enough
     * to accept all players, of course).
     *
     * @param partyMembers A {@link Set} containing the {@link UUID} of the party's members.
     *
     * @return A {@link Pair} instance, where:
     * <ul>
     *     <li>
     *         the left value is a {@link Boolean}, saying if the party is allowed to join
     *         ({@code true}) or not ({@code false});
     *     </li>
     *     <li>
     *         the right value is the error message displayed, if the connection
     *         is refused; ignored else.
     *     </li>
     * </ul>
     */
    public Pair<Boolean, String> canPartyJoinGame(List<UUID> partyMembers)
    {
        return Pair.of(true, "");
    }

    /**
     * Checks if the given player is spectating or not.
     *
     * @param player The player.
     * @return {@code true} if spectating.
     */
    public boolean isSpectator(Player player)
    {
        if (this.gamePlayers.containsKey(player.getUniqueId()))
            return this.gamePlayers.get(player.getUniqueId()).isSpectator();
        else
            return true;
    }

    /**
     * Checks if the given player is moderating or not.
     *
     * @param player The player.
     * @return {@code true} if moderating.
     */
    public boolean isModerator(Player player)
    {
        return this.gameModerators.contains(player.getUniqueId());
    }

    /**
     * Checks if the game is started, i.e. all status after the beginning of the game
     * (started, finished, rebooting).
     *
     * @return {@code true} if the game is started.
     */
    public boolean isGameStarted()
    {
        return this.status == Status.IN_GAME || this.status == Status.FINISHED || this.status == Status.REBOOTING;
    }
}
