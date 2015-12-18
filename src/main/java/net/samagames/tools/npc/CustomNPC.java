package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.Player;

/**
 * Created by Silva on 18/12/2015.
 */
public class CustomNPC extends EntityHuman {

    private NPCInteractCallback callback;

    public CustomNPC(World world, GameProfile gameprofile) {
        super(world, gameprofile);
    }

    public void onInteract(boolean rightClick, Player damager)
    {
        if(callback != null)
            callback.done(rightClick, damager);
    }

    public void setCallback(NPCInteractCallback callback)
    {
        this.callback = callback;
    }

    public NPCInteractCallback getCallback()
    {
        return callback;
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
