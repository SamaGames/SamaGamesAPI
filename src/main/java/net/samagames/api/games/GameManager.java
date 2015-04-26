package net.samagames.api.games;

import net.samagames.api.games.themachine.CoherenceMachine;
import net.samagames.api.signs.SignBuilder;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GameManager
{
    /**
     * Register the arena
     *
     * @param game Arena object
     */
    void registerGame(IManagedGame game);

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
     */
    void onPlayerReconnectTimeOut(Player player);

    /**
     * Refresh the arena to the hubs
     */
    void refreshArena();

    /**
     * Refresh the arena to the hubs with a custom SignBuilder
     *
     * @param builder Custom SignBuilder
     */
    void refreshArena(SignBuilder builder);

    /**
     * Set the status of the game (we'll refresh automatically the arena)
     *
     * @param status The status of the game
     */
    void setStatus(Status status);

    /**
     * Set if the game allow reconnect
     *
     * @param flag True or False
     */
    void allowReconnect(boolean flag);

    /**
     * Set the reconnect time for a player if reconnect supported
     *
     * @param minutes Time in minute
     */
    void setMaxReconnectTime(int minutes);

    /**
     * Get the registered game
     *
     * @return The registered game (null if none)
     */
    IManagedGame getGame();

    /**
     * Get the CoherenceMachine for the registered game
     *
     * @return The CoherenceMachine (null if no game)
     */
    CoherenceMachine getCoherenceMachine();

    /**
     * Return if a player is waited for a reconnect
     *
     * @param uuid UUID of the player
     * @return True or False
     */
    boolean isWaited(UUID uuid);

    /**
     * Return if the game support the reconnect
     *
     * @return True or False
     */
    boolean isReconnectAllowed();
}
