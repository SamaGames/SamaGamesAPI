package net.samagames.core.api.network;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinManager;
import net.samagames.api.network.JoinResponse;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

public class JoinManagerImplement implements JoinManager, Listener {

    protected TreeMap<Integer, JoinHandler> handlerTreeMap = new TreeMap<>();
    protected HashSet<UUID> moderatorsExpected = new HashSet<>();
    protected HashSet<UUID> playersExpected = new HashSet<>();
    protected boolean isPartyLimited;

    public JoinManagerImplement() {
        isPartyLimited = !SamaGamesAPI.get().getServerName().startsWith("Lobby");
    }

    public void setPartyLimited(boolean value) {
        this.isPartyLimited = value;
    }

    public boolean isPartyLimited() { // This method can be overrided
        return isPartyLimited;
    }

    @Override
    public void registerHandler(JoinHandler handler, int priority) {
        this.handlerTreeMap.put(priority, handler);
    }

    void requestJoin(UUID player) {
        // Check party :
        UUID party = SamaGamesAPI.get().getPartiesManager().getPlayerParty(player);
        if (party != null && isPartyLimited()) {
            if (!SamaGamesAPI.get().getPartiesManager().getLeader(party).equals(player)) {
                TextComponent refuse = new TextComponent("Seul le chef de partie peur rejoindre un jeu.");
                refuse.setColor(ChatColor.RED);
                SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player).sendMessage(refuse);
                return;
            } else {
                requestPartyJoin(party);
                return;
            }
        }

        JoinResponse response = dispatchRequestJoin(player);

        if (!response.isAllowed()) {
            TextComponent refuse = new TextComponent(response.getReason());
            refuse.setColor(ChatColor.RED);
            SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player).sendMessage(refuse);
        } else {
            playersExpected.add(player);
            Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> playersExpected.remove(player), 20*15L);
            SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player).connect(SamaGamesAPI.get().getServerName());
        }
    }

    JoinResponse dispatchRequestJoin(UUID player) {
        JoinResponse response = new JoinResponse();
        for (JoinHandler handler : handlerTreeMap.values())
            response = handler.requestJoin(player, response);

        return response;
    }

    JoinResponse requestPartyJoin(UUID partyID) {
        return requestPartyJoin(partyID, false);
    }

    JoinResponse requestPartyJoin(UUID partyID, boolean localPlayer) {
        UUID leader = SamaGamesAPI.get().getPartiesManager().getLeader(partyID);
        Set<UUID> members = SamaGamesAPI.get().getPartiesManager().getPlayersInParty(partyID).keySet();

        JoinResponse response = new JoinResponse();
        for (JoinHandler handler : handlerTreeMap.values())
            response = handler.requestPartyJoin(leader, members, response);

        if (response.isAllowed()) {
            for (UUID player : members) {
                playersExpected.add(player);
                Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () -> playersExpected.remove(player), 20 * 15L);
                SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player).connect(SamaGamesAPI.get().getServerName());
            }
        } else if (!localPlayer) {
            TextComponent component = new TextComponent("Impossible de vous connecter : " + response.getReason());
            component.setColor(net.md_5.bungee.api.ChatColor.RED);
            SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(leader).sendMessage(component);
        }

        return response;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        UUID player = event.getUniqueId();

        if (moderatorsExpected.contains(player)) // On traite après
            return;

        if (!playersExpected.contains(player)) {
            UUID party = SamaGamesAPI.get().getPartiesManager().getPlayerParty(player);
            if (party != null && isPartyLimited()) {
                if (!SamaGamesAPI.get().getPartiesManager().getLeader(party).equals(player)) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "Seul le chef de partie peur rejoindre un jeu.");
                    return;
                } else {
                    JoinResponse response = requestPartyJoin(party);
                    if (!response.isAllowed()) {
                        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, response.getReason());
                        return;
                    }
                }
            } else {
                JoinResponse response = dispatchRequestJoin(player);
                if (!response.isAllowed()) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, response.getReason());
                    return;
                }
            }
        }

        for (JoinHandler handler : handlerTreeMap.values())
            handler.onLogin(player);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (moderatorsExpected.contains(player.getUniqueId())) {
            for (JoinHandler handler : handlerTreeMap.values())
                handler.onModerationJoin(player);

            return;
        }

        for (JoinHandler handler : handlerTreeMap.values())
            handler.finishJoin(player);

		// Enregistrement du joueur
		APIPlugin.getInstance().getExecutor().addTask(() -> {
            Jedis jedis = SamaGamesAPI.get().getBungeeResource();
            jedis.sadd("connectedonserv:" + APIPlugin.getInstance().getServerName(), player.getUniqueId().toString());
            jedis.close();
        });
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
}
