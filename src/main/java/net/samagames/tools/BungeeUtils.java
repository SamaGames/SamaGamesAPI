package net.samagames.tools;

import net.samagames.core.APIPlugin;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class BungeeUtils
{
    public static void sendPlayerToServer(Player player, String server)
    {
        APIPlugin.log(Level.INFO, "Sending player '" + player.getName() + "' to '" + server + "'...");

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try
        {
            out.writeUTF("Connect");
            out.writeUTF(server);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        player.sendPluginMessage(APIPlugin.getInstance(), "BungeeCord", b.toByteArray());
    }
}
