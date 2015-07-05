package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.network.IProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class BungeeUtils
{
    public static void sendPlayerToServer(Player player, String server)
    {
        SamaGamesAPI.get().log(Level.INFO, "Sending player '" + player.getName() + "' to '" + server + "'...");

        IProxiedPlayer proxiedPlayer = SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player.getUniqueId());
        proxiedPlayer.connect(server);
    }
}
