package net.samagames.tools.npc.nms;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityAmbient;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

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
public class NametagEntity extends EntityAmbient
{
    public NametagEntity(final Player player)
    {
        super(((CraftWorld)player.getWorld()).getHandle());
        final Location location = player.getLocation();
        this.setInvisible(true);
        this.setPosition(location.getX(), location.getY(), location.getZ());
        try {
            final Field invulnerable = Entity.class.getDeclaredField("invulnerable");
            invulnerable.setAccessible(true);
            invulnerable.setBoolean(this, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        this.persistent = true;
        this.hideTag(player);
    }

    public void hideTag(final Player player)
    {
        ((CraftPlayer)player).getHandle().passengers.add(this);
    }

    public void B_()
    {
        final double motX = 0.0;
        this.motZ = motX;
        this.motY = motX;
        this.motX = motX;
        this.a(0.0f, 0.0f);
        this.a(0.0f, 0.0f, 0.0f);
    }

    public void C(final Entity entity)
    {
    }
}