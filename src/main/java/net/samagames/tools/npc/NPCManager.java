package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.World;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import net.samagames.tools.Reflection;
import net.samagames.tools.gameprofile.ProfileLoader;
import net.samagames.tools.holograms.Hologram;
import net.samagames.tools.npc.nms.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.util.*;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager implements Listener {

    public SamaGamesAPI api;

    private Map<CustomNPC, Hologram> entities = new HashMap<>();

    private CallBack<CustomNPC> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    private void updateForAllNPC(CustomNPC npc)
    {
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        /*for(Player p : players)
        {
            sendNPC(p, npc);
        }*/
    }

    public void sendNPC(Player p, CustomNPC npc)
    {
        Reflection.sendPacket(p, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

        p.hidePlayer(npc.getBukkitEntity());
        p.showPlayer(npc.getBukkitEntity());

        this.api.getPlugin().getServer().getScheduler().runTaskLater(this.api.getPlugin(), () ->
        {
            Reflection.sendPacket(p, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
        }, 60L);
    }

    public void removeNPC(Player p, CustomNPC npc)
    {
        p.hidePlayer(npc.getBukkitEntity());
    }

    /**
     * Need to be called async
     * @param location
     * @param skinUUID
     * @return
     */
    public CustomNPC createNPC(Location location, UUID skinUUID)
    {
        return createNPC(location, skinUUID, new String[] { "[NPC] " + entities.size() });
    }

    public CustomNPC createNPC(Location location, UUID skinUUID, String[] hologramLines)
    {
        return createNPC(location, skinUUID, hologramLines, true);
    }

    public CustomNPC createNPC(Location location, UUID skinUUID, String[] hologramLines, boolean showByDefault)
    {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = new ProfileLoader(skinUUID.toString(), "[NPC] " + entities.size(), skinUUID).loadProfile();

        CustomNPC npc = new CustomNPC(world, gameProfile);

        npc.setLocation(location);

        Hologram hologram = null;

        if (hologramLines != null)
        {
            hologram = new Hologram(hologramLines);
            hologram.generateLines(location.clone().add(0.0D, 1.8D, 0.0D));
        }

        world.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);

        npc.setHologram(hologram);
        entities.put(npc, hologram);

        if (scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        if (showByDefault)
        {
            for(Player player : Bukkit.getOnlinePlayers())
            {
                sendNPC(player, npc);

                if (hologram != null)
                    hologram.addReceiver(player);
            }
        }

        //Bukkit.getScheduler().runTaskLater(api.getPlugin(), () -> updateForAllNPC(npc), 2L);
        return npc;
    }

    public void removeNPC(String name)
    {
        removeNPC(getNPCEntity(name));
    }

    public void removeNPC(CustomNPC npc)
    {
        if (npc != null)
        {
            if (npc.getHologram() != null)
                npc.getHologram().destroy();

            for (Player p : Bukkit.getOnlinePlayers())
                removeNPC(p, npc);

            npc.getWorld().removeEntity(npc);
        }
    }

    public CustomNPC getNPCEntity(String name)
    {
        for (CustomNPC entity : entities.keySet())
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
        if (Reflection.getHandle(event.getEntity()) instanceof CustomNPC && event.getDamager() instanceof Player)
        {
            CustomNPC npc = (CustomNPC) Reflection.getHandle(event.getEntity());
            npc.onInteract(false, (Player) event.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteractNPC(PlayerInteractEntityEvent event)
    {
        if (Reflection.getHandle(event.getRightClicked()) instanceof CustomNPC)
        {
            CustomNPC npc = (CustomNPC) Reflection.getHandle(event.getRightClicked());
            npc.onInteract(true, event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        this.entities.keySet().forEach(customNPC ->
        {
            if (event.getFrom().distanceSquared(customNPC.getBukkitEntity().getLocation()) > 2500
                    && event.getTo().distanceSquared(customNPC.getBukkitEntity().getLocation()) < 2500)
                sendNPC(event.getPlayer(), customNPC);
        });
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        this.entities.keySet().forEach(customNPC ->
        {
            if (event.getFrom().distanceSquared(customNPC.getBukkitEntity().getLocation()) > 2500
                    && event.getTo().distanceSquared(customNPC.getBukkitEntity().getLocation()) < 2500)
                sendNPC(event.getPlayer(), customNPC);
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Bukkit.getScheduler().runTaskLater(api.getPlugin(), () -> entities.keySet().forEach(customNPC ->
        {
            sendNPC(event.getPlayer(), customNPC);
            entities.get(customNPC).addReceiver(event.getPlayer());
        }), 2L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerKickEvent event)
    {
        this.entities.entrySet().forEach(customNPC ->
                customNPC.getValue().removeReceiver(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        this.entities.entrySet().forEach(customNPC -> customNPC.getValue().removeReceiver(event.getPlayer()));
    }
}
