package net.samagames.tools;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.samagames.hub.Hub;
import org.bukkit.entity.Player;

public class BungeeUtils
{
    public static void sendPlayerToServer(Player player, String server)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(server);

        Hub.getInstance().getServer().sendPluginMessage(Hub.getInstance(), "BungeeCord", out.toByteArray());
    }
}
