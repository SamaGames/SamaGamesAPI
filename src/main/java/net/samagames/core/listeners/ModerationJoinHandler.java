package net.samagames.core.listeners;

import com.rabbitmq.client.QueueingConsumer;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.network.JoinHandler;
import net.samagames.permissionsbukkit.PermissionsBukkit;
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
	public void receive(QueueingConsumer.Delivery delivery) {
		String[] args = new String(delivery.getBody()).split(" ");
		UUID uuid = UUID.fromString(args[1]);
		UUID target = UUID.fromString(args[2]);
		if (PermissionsBukkit.hasPermission(uuid, "games.modjoin")) {
			teleportTargets.put(uuid, target);
		}
	}
}
