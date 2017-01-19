package net.samagames.tools.cameras;

import net.minecraft.server.v1_10_R1.*;

public class EntityCamera extends EntityArmorStand
{
    EntityCamera(World world)
    {
        super(world);

        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.setMarker(true);
    }
}
