package net.samagames.core.listeners;

import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.network.JoinHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by vialarl on 09/03/2015.
 */
public class ModerationJoinHandler implements JoinHandler, PacketsReceiver {

    protected HashMap<UUID, UUID> teleportTargets = new HashMap<>();

    @Override
    public void onModerationJoin(Player player) {
        player.sendMessage(ChatColor.GOLD + "Vous avez rejoint cette arène en mode modération.");
        if (teleportTargets.containsKey(player.getUniqueId())) {
            UUID target = teleportTargets.get(player.getUniqueId());
            Player tar = Bukkit.getPlayer(target);
            if (tar != null)
                player.teleport(tar);
            teleportTargets.remove(player.getUniqueId());
        }
    }

    @Override
    public void receive(String channel, String packet) {
        if (packet.startsWith("teleport")) {
            try  {
                String[] args = packet.split(" ");
                UUID uuid = UUID.fromString(args[1]);
                UUID target = UUID.fromString(args[2]);
                if (PermissionsBukkit.hasPermission(uuid, "games.modjoin")) {
                    teleportTargets.put(uuid, target);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
