package net.samagames.tools;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
