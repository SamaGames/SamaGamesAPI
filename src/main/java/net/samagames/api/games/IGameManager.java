package net.samagames.api.games;

import net.samagames.api.games.themachine.ICoherenceMachine;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Game manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IGameManager
{
    /**
     * Register the arena
     *
     * @param game Arena object
     */
    void registerGame(Game game);

    /**
     * Kick a player from the game
     *
     * @param player The player
     * @param reason Reason of the kick
     */
    void kickPlayer(Player player, String reason);

    /**
     * Event fired when a player disconnect from the game
     *
     * @param player The Player
     */
    void onPlayerDisconnect(Player player);

    /**
     * Event fired when a player reconnect from the game (Only for supported game)
     *
     * @param player The Player
     */
    void onPlayerReconnect(Player player);

    /**
     * Event fired the reconnect time for a player is out (Only for supported game)
     *
     * @param player The Player
     * @param silent Message displaying
     */
    void onPlayerReconnectTimeOut(OfflinePlayer player, boolean silent);

    /**
     * Refresh the arena to the hubs
     */
    void refreshArena();

    /**
     * Set the reconnect time for a player.
     *
     * <b>DISCLAIMER - TO ENABLE RECONNECT PLEASE USE THIS FUNCTION AND SET A TIME</b>
     *
     * @param minutes Time in minute
     */
    void setMaxReconnectTime(int minutes);

    /**
     * Disable automatic mechanisms of the game
     *
     * @param freeMode {@code true} if activated
     */
    void setFreeMode(boolean freeMode);

    /**
     * Enable the Minecraft 1.8 based PvP mechanics to bypass 1.9's
     *
     * @param legacyPvP {@code true} if activated
     */
    void setLegacyPvP(boolean legacyPvP);

    /**
     * Set an optional statistics helper to never forget the statistics
     * to increase
     *
     * @param gameStatisticsHelper Statistics helper instance
     */
    void setGameStatisticsHelper(IGameStatisticsHelper gameStatisticsHelper);

    /**
     * Get the registered game
     *
     * @return The registered game (null if none)
     */
    Game getGame();

    /**
     * Get the status of the game
     *
     * @return The status (null if arena is null)
     */
    Status getGameStatus();

    /**
     * Get the CoherenceMachine for the registered game
     *
     * @return The CoherenceMachine (null if no game)
     */
    ICoherenceMachine getCoherenceMachine();

    /**
     * Get the properties of the game (if is a game server)
     *
     * @return The properties
     */
    IGameProperties getGameProperties();

    /**
     * Get the game implemented gui manager
     *
     * @return The implementation
     */
    GameGuiManager getGameGuiManager();

    /**
     * Get the game statistics helper
     *
     * @return The implementation
     */
    IGameStatisticsHelper getGameStatisticsHelper();

    /**
     * Get the max reconnect time in minutes
     *
     * @return The time
     */
    int getMaxReconnectTime();

    /**
     * Return if a player is waited for a reconnect
     *
     * @param uuid UUID of the player
     * @return True or False
     */
    boolean isWaited(UUID uuid);

    /**
     * Return if the game's automatic mechanisms are disabled
     *
     * @return True or False
     */
    boolean isFreeMode();

    /**
     * Return if the Minecraft 1.8 based PvP mechanics are enabled
     * to bypass 1.9's
     *
     * @return True or False
     */
    boolean isLegacyPvP();

    /**
     * Return if the game support the reconnect
     *
     * @param player The player
     * @return True or False
     */
    boolean isReconnectAllowed(Player player);

    /**
     * Return if the game support the reconnect
     *
     * @param player The player
     * @return True or False
     */
    boolean isReconnectAllowed(UUID player);

    /**
     *  Define if the cache of a player will be deleted att disconnection (playerdata, permission, etc)
     *  @param keepIt true for keep the cache, false to let it go
     */
    void setKeepPlayerCache(boolean keepIt);

    /**
     * Get if the cache is currently kept after a layer disconnection
     * @return true to keep it, false to let it go
     */
    boolean isKeepingPlayerCache();

    /**
     * Network use to set the timestamp of the start of the game
     */
    void startTimer();

    /**
     * Network use to stop the game and save the server info to database for stats
     */
    void stopTimer();
}
