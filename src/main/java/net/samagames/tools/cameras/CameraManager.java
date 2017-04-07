package net.samagames.tools.cameras;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;
import java.util.logging.Level;

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

        if (Reflection.PackageType.getServerVersion().equals("v1_8_R3"))
        {
            api.log(Level.WARNING, "The Camera API is available only on 1.9 and more servers.");
            return;
        }

        Class<?> entityBatClass = Reflection.getNMSClass("EntityBat");

        EntityRegistrar.registerEntity("FakeCamera", 65, entityBatClass, EntityCamera.class);
        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    /**
     * Create a fake camera
     *
     * @param initialPosition The position where the camera has
     *                        to spawn
     * @param fixed Has the player to be fixed into the camera
     *
     * @return The camera instance
     */
    public Camera createCamera(Location initialPosition, boolean fixed)
    {
        Camera camera = new Camera(initialPosition, fixed);
        this.cameras.add(camera);

        return camera;
    }

    /**
     * Remove the camera from the world
     *
     * @param camera The camera instance
     */
    public void removeCamera(Camera camera)
    {
        this.removeCamera(camera, null);
    }

    /**
     * Remove the camera from the world
     *
     * @param camera The camera instance
     * @param backPosition The position the player has to
     *                     be teleported after the camera is
     *                     removed
     */
    public void removeCamera(Camera camera, Location backPosition)
    {
        camera.remove(backPosition);
        this.cameras.remove(camera);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
    {
        Camera camera = this.getPlayerCamera(event.getPlayer());

        if (camera != null && camera.isFixed())
            event.setCancelled(true);
    }

    /**
     * Get the camera the player is viewing into
     *
     * @param player The player
     *
     * @return The camera instance
     */
    public Camera getPlayerCamera(Player player)
    {
        for (Camera camera : this.cameras)
            if (camera.isPlayerViewing(player))
                return camera;

        return null;
    }
}
