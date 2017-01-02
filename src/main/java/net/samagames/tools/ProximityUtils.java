package net.samagames.tools;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 26/12/2016
 */
public class ProximityUtils
{
    public static List<Entity> getNearbyEntities(Location center, double radius)
    {
        return getNearbyEntities(center, radius, null);
    }

    public static List<Entity> getNearbyEntities(Location center, double radius, EntityType filter)
    {
        double chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        List<Entity> entities = new ArrayList<>();

        for (double chX = 0 - chunkRadius; chX <= chunkRadius; chX++)
        {
            for (double chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++)
            {
                double x = center.getX();
                double y = center.getY();
                double z = center.getZ();

                for (Entity entity : new Location(center.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities())
                {
                    if (filter != null && entity.getType() != filter)
                        continue;

                    if (entity.getLocation().distance(center) <= radius && entity.getLocation().getBlock() != center.getBlock())
                        entities.add(entity);
                }
            }
        }

        return entities;
    }

    public static <ENTITY extends Entity> BukkitTask onNearbyOf(JavaPlugin plugin, Entity entity, double offsetX, double offsetY, double offsetZ, Class<ENTITY> filter, Consumer<ENTITY> callback)
    {
        return plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () ->
        {
            entity.getNearbyEntities(offsetX, offsetY, offsetZ).forEach(found ->
            {
                if (filter == null || filter.isAssignableFrom(found.getClass()))
                    plugin.getServer().getScheduler().runTask(plugin, () -> callback.accept((ENTITY) found));
            });
        }, 2L, 2L);
    }
}
