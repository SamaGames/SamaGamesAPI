package net.samagames.tools.npc.nms;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_9_R1.*;
import net.samagames.tools.npc.NPCInteractCallback;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;

/**
 * Created by Silva on 18/12/2015.
 */
public class CustomNPC extends EntityPlayer {

    private NPCInteractCallback callback;

    public CustomNPC(World world, GameProfile gameprofile, PlayerInteractManager playerInteractManager)
    {
        super(world.getServer().getServer(), (WorldServer) world, gameprofile, playerInteractManager);
        playerInteractManager.b(WorldSettings.EnumGamemode.SURVIVAL);
        this.playerConnection = new VoidPlayerConnection(world.getServer().getServer(), this);
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

    public void setLocation(Location loc)
    {
        setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public void m() {

    }

    @Override
    protected void v() {

    }

    @Override
    public void attack(Entity entity) {

    }

    @Override
    public String A() {
        return "";
    }

    @Override
    public void d(Entity entity) {

    }

    @Override
    public void setSpectatorTarget(Entity entity) {

    }

    @Override
    public void updateWeather(float oldRain, float newRain, float oldThunder, float newThunder) {

    }

    @Override
    public void setPlayerWeather(WeatherType type, boolean plugin) {

    }
}
