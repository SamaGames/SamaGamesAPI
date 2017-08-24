package net.samagames.tools.cameras;

import net.minecraft.server.v1_12_R1.EntityBat;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class CameraManager implements Listener
{
    private final List<Camera> cameras;

    public CameraManager(SamaGamesAPI api)
    {
        this.cameras = new ArrayList<>();

        EntityRegistrar.registerEntity("FakeCamera", 65, EntityBat.class, EntityCamera.class);
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
