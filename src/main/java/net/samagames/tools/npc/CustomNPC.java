package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;

/**
 * Created by Silva on 18/12/2015.
 */
public class CustomNPC extends EntityHuman {

    public CustomNPC(World world, GameProfile gameprofile) {
        super(world, gameprofile);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public void t_() {

    }

    @Override
    protected void s() {

    }

    @Override
    public void attack(Entity entity) {

    }
}
