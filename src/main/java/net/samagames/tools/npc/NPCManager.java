package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_9_R2.*;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import net.samagames.tools.gameprofile.ProfileLoader;
import net.samagames.tools.npc.nms.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager implements Listener {

    public SamaGamesAPI api;

    private List<CustomNPC> entities = new ArrayList<>();

    private CallBack<CustomNPC> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    @EventHandler
    public void onPlayerConnectionHook(PlayerJoinEvent event)
    {
        Bukkit.getScheduler().runTaskLater(api.getPlugin(), () ->
        {
            for (CustomNPC npc : entities)
                updateNPC(event.getPlayer(), npc);
        }, 2L);
    }

    public void updateForAllNPC(CustomNPC npc)
    {
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        for(Player p : players)
        {
            updateNPC(p, npc);
        }
    }

    public void updateNPC(Player p, CustomNPC npc)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
    }

    /**
     * Need to be called async
     * @param location
     * @param skinUUID
     * @return
     */
    public CustomNPC createNPC(Location location, UUID skinUUID)
    {
        return createNPC(location, skinUUID, "[NPC] " + entities.size());
    }

    public CustomNPC createNPC(Location location, UUID skinUUID, String displayName)
    {
        final World w = ((CraftWorld) location.getWorld()).getHandle();

        ProfileLoader profileLoader = new ProfileLoader(UUID.randomUUID().toString(), displayName, skinUUID.toString());
        GameProfile gameProfile = profileLoader.loadProfile();

        final CustomNPC npc = new CustomNPC(w, gameProfile, new PlayerInteractManager(w));
        npc.setLocation(location);
        npc.setCustomName(displayName);
        npc.setCustomNameVisible(true);
        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entities.add(npc);
        if (scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        Bukkit.getScheduler().runTaskLater(api.getPlugin(), () -> updateForAllNPC(npc), 2L);
        return npc;
    }

    public void removeNPC(String name)
    {
        CustomNPC npc = getNPCEntity(name);
        if (npc != null)
            npc.getWorld().removeEntity(npc);
    }

    public CustomNPC getNPCEntity(String name)
    {
        for (CustomNPC entity : entities)
            if (entity.getName().equals(name))
                return entity;
        return null;
    }

    public void setScoreBoardRegister(CallBack<CustomNPC> scoreBoardRegister) {
        this.scoreBoardRegister = scoreBoardRegister;
    }

    @EventHandler
    public void onPlayerHitNPC(EntityDamageByEntityEvent event)
    {
        if (((CraftEntity)event.getEntity()).getHandle() instanceof CustomNPC && event.getDamager() instanceof Player)
        {
            CustomNPC npc = (CustomNPC) ((CraftEntity)event.getEntity()).getHandle();
            npc.onInteract(false, (Player) event.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteractNPC(PlayerInteractEntityEvent event)
    {
        if (((CraftEntity)event.getRightClicked()).getHandle() instanceof CustomNPC)
        {
            CustomNPC npc = (CustomNPC) ((CraftEntity)event.getRightClicked()).getHandle();
            npc.onInteract(true, event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        this.entities.forEach(customNPC ->
        {
            if (event.getFrom().distanceSquared(customNPC.getBukkitEntity().getLocation()) > 2500
                    && event.getTo().distanceSquared(customNPC.getBukkitEntity().getLocation()) < 2500)
                updateNPC(event.getPlayer(), customNPC);
        });
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        this.entities.forEach(customNPC ->
        {
            if (event.getFrom().distanceSquared(customNPC.getBukkitEntity().getLocation()) > 2500
                    && event.getTo().distanceSquared(customNPC.getBukkitEntity().getLocation()) < 2500)
                updateNPC(event.getPlayer(), customNPC);
        });
    }
}
