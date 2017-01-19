package net.samagames.tools.cameras;

import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 18/01/2017
 */
public class Camera
{
    private final ConcurrentMap<UUID, GameMode> viewers;
    private final boolean fixed;
    private final EntityCamera entityCamera;

    Camera(Location initialPosition, boolean fixed)
    {
        this.viewers = new ConcurrentHashMap<>();
        this.fixed = fixed;

        World world = ((CraftWorld) initialPosition.getWorld()).getHandle();
        this.entityCamera = new EntityCamera(world);

        this.entityCamera.setPosition(initialPosition.getX(), initialPosition.getY(), initialPosition.getZ());
        world.addEntity(this.entityCamera, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    void remove(Location backPosition)
    {
        this.viewers.keySet().stream().filter(viewer -> Bukkit.getPlayer(viewer) != null).forEach(viewer -> this.removeViewer(Bukkit.getPlayer(viewer), backPosition));
        this.entityCamera.die();
    }

    public void addViewer(Player player)
    {
        this.viewers.put(player.getUniqueId(), player.getGameMode());

        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(this.entityCamera.getBukkitEntity());
    }

    public void removeViewer(Player player)
    {
        this.removeViewer(player, null);
    }

    public void removeViewer(Player player, Location backPosition)
    {
        player.setSpectatorTarget(null);
        player.setGameMode(this.viewers.get(player.getUniqueId()));

        if (backPosition != null)
            player.teleport(backPosition);

        this.viewers.remove(player.getUniqueId());
    }

    public EntityCamera getEntityCamera()
    {
        return this.entityCamera;
    }

    public boolean isPlayerViewing(Player player)
    {
        return this.viewers.containsKey(player.getUniqueId());
    }

    public boolean isFixed()
    {
        return this.fixed;
    }
}
