package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.network.ProxiedPlayer;
import net.samagames.core.APIPlugin;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class BungeeUtils
{
    public static void sendPlayerToServer(Player player, String server)
    {
        APIPlugin.log(Level.INFO, "Sending player '" + player.getName() + "' to '" + server + "'...");

        ProxiedPlayer proxiedPlayer = SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player.getUniqueId());
        proxiedPlayer.connect(server);
    }
}
