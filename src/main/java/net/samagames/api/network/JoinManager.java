package net.samagames.api.network;

import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.UUID;

public interface JoinManager extends Listener {

    /**
     * Register a JoinHandler which is going to be called by the manager
     * @param handler The handler which will handle join requests
     * @param priority The handler priority (0 = Lowest, please do not use priorities under 10)
     */
    public void registerHandler(JoinHandler handler, int priority);

    public int countExpectedPlayers();
    public HashSet<UUID> getExpectedPlayers();

}
