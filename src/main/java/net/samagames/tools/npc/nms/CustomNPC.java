package net.samagames.tools.npc.nms;

import com.mojang.authlib.GameProfile;
import net.samagames.tools.holograms.Hologram;
import net.minecraft.server.v1_10_R1.*;
import net.samagames.tools.npc.NPCInteractCallback;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Silva on 18/12/2015.
 */
public class CustomNPC extends EntityPlayer {

    private NPCInteractCallback callback;

    private Hologram hologram;

    public CustomNPC(World world, GameProfile gameprofile, PlayerInteractManager playerInteractManager)
    {
        super(world.getServer().getServer(), (WorldServer) world, gameprofile, playerInteractManager);
        playerInteractManager.b(EnumGamemode.SURVIVAL);
        this.playerConnection = new VoidPlayerConnection(world.getServer().getServer(), this);
        this.getDataWatcher().set(br, Byte.MAX_VALUE);
    }

    public void onInteract(boolean rightClick, Player damager)
    {
        if(callback != null)
            callback.done(rightClick, damager);
    }

    public CustomNPC setCallback(NPCInteractCallback callback)
    {
        this.callback = callback;
        return this;
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource.getEntity() == null || !(damagesource.getEntity() instanceof EntityPlayer))
            return true;
        EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent(damagesource.getEntity().getBukkitEntity(), this.getBukkitEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, 0);
        entityDamageByEntityEvent.setCancelled(true);
        Bukkit.getPluginManager().callEvent(entityDamageByEntityEvent);
        return true;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }
}
