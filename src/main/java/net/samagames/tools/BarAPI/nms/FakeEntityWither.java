package net.samagames.tools.BarAPI.nms;


import net.minecraft.server.v1_9_R1.EntityWither;
import net.minecraft.server.v1_9_R1.World;

/**
 * Fake entity wither class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class FakeEntityWither extends EntityWither
{
    public FakeEntityWither(World world)
    {
        super(world);
        setSize(0.0F, 0.0F);
    }

    @Override
    public void g(float sideMot, float forMot)
    {
        this.motX = 0;
        this.motY = 0;
        this.motZ = 0;
        this.yaw = 0;
        this.pitch = 0;

        sideMot = 0;
        forMot = 0;
    }
}
