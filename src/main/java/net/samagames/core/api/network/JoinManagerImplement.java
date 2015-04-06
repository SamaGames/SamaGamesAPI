package net.samagames.core.api.network;

import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinManager;
import net.samagames.api.network.JoinResponse;
import net.samagames.core.APIPlugin;
import net.samagames.core.TasksExecutor;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.UUID;

public class JoinManagerImplement implements JoinManager, PacketsReceiver, Listener {

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
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
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

		// Enregistrement du joueur
		APIPlugin.getInstance().getExecutor().addTask(() -> {
			Jedis jedis = SamaGamesAPI.get().getBungeeResource();
			jedis.sadd("connectedonserv:" + APIPlugin.getInstance().getServerName(), player.getUniqueId().toString());
			jedis.close();
		});
    }

    public void joinParty(UUID partyId) {



    }

    @EventHandler
    public void onLogout(final PlayerQuitEvent event) {
        if (moderatorsExpected.contains(event.getPlayer().getUniqueId()))
            moderatorsExpected.remove(event.getPlayer().getUniqueId());

        for (JoinHandler handler : handlerTreeMap.values())
            handler.onLogout(event.getPlayer());

		APIPlugin.getInstance().getExecutor().addTask(() -> {
			Jedis jedis = SamaGamesAPI.get().getBungeeResource();
			jedis.srem("connectedonserv:" + APIPlugin.getInstance().getServerName(), event.getPlayer().getUniqueId().toString());
			jedis.close();
		});
    }

    public void addModerator(UUID moderator) {
        moderatorsExpected.add(moderator);
    }

    @Override
    public void receive(String channel, String packet) {
        if (packet.startsWith("moderator")) {
            String id = packet.split(" ")[1];
            UUID uuid = UUID.fromString(id);
            if (PermissionsBukkit.hasPermission(uuid, "games.modjoin"))
                moderatorsExpected.add(uuid);
        }
    }
}
