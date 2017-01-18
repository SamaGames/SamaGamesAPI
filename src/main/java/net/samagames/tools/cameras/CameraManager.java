package net.samagames.tools.cameras;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 18/01/2017
 */
public class CameraManager implements Listener
{
    private final List<Camera> cameras;

    public CameraManager(SamaGamesAPI api)
    {
        this.cameras = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    public Camera createCamera(Location initialPosition, boolean fixed)
    {
        Camera camera = new Camera(initialPosition, fixed);
        this.cameras.add(camera);

        return camera;
    }

    public void removeCamera(Camera camera)
    {
        camera.remove();
        this.cameras.remove(camera);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
    {
        Camera camera = this.getPlayerCamera(event.getPlayer());

        if (camera != null && camera.isFixed())
            event.setCancelled(true);
    }

    public Camera getPlayerCamera(Player player)
    {
        for (Camera camera : this.cameras)
            if (camera.isPlayerViewing(player))
                return camera;

        return null;
    }
}
