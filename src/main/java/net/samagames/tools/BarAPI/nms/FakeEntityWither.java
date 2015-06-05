package net.samagames.tools.BarAPI.nms;


import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.World;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by Geekpower14 on 02/06/2015.
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */
public class FakeEntityWither extends EntityWither {

    public FakeEntityWither(World world) {
        super(world);
        setSize(0.0F, 0.0F);
    }

    @Override
    public void g(float sideMot, float forMot) {
        this.motX = 0;
        this.motY = 0;
        this.motZ = 0;
        this.yaw = 0;
        this.pitch = 0;
        sideMot = 0;
        forMot = 0;
    }
}
