package net.samagames.core.api.network;

import com.rabbitmq.client.QueueingConsumer;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinManager;
import net.samagames.api.network.JoinResponse;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.UUID;

public class JoinManagerImplement implements JoinManager, PacketsReceiver {

    protected TreeMap<Integer, JoinHandler> handlerTreeMap = new TreeMap<>();
    protected HashSet<UUID> moderatorsExpected = new HashSet<>();

    @Override
    public void registerHandler(JoinHandler handler, int priority) {
        this.handlerTreeMap.put(priority, handler);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        UUID player = event.getUniqueId();

        if (moderatorsExpected.contains(player)) // On traite après
            return;

        JoinResponse response = new JoinResponse();
        for (JoinHandler handler : handlerTreeMap.values())
            response = handler.onLogin(player, response);

        if (!response.isAllowed())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, response.getReason());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (moderatorsExpected.contains(player.getUniqueId())) {
            for (JoinHandler handler : handlerTreeMap.values())
                handler.onModerationJoin(player);

            return;
        }

        JoinResponse response = new JoinResponse();
        for (JoinHandler handler : handlerTreeMap.values())
            response = handler.onJoin(player, response);

        if (!response.isAllowed())
            player.kickPlayer(response.getReason());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        if (moderatorsExpected.contains(event.getPlayer().getUniqueId()))
            moderatorsExpected.remove(event.getPlayer().getUniqueId());

        for (JoinHandler handler : handlerTreeMap.values())
            handler.onLogout(event.getPlayer());
    }

    public void addModerator(UUID moderator) {
        moderatorsExpected.add(moderator);
    }


	@Override
	public void receive(QueueingConsumer.Delivery delivery) {
		UUID uuid = UUID.fromString(new String(delivery.getBody()));
		if (PermissionsBukkit.hasPermission(uuid, "games.modjoin"))
			moderatorsExpected.add(uuid);
	}
}
