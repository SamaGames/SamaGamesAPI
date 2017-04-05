package net.samagames.tools.npc.nms.compat.v1_9_R2;

import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityAmbient;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

public class NametagEntity19 extends EntityAmbient
{
    public NametagEntity19(final Player player)
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

    public void m()
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