package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_10_R1.PlayerInteractManager;
import net.minecraft.server.v1_10_R1.World;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import net.samagames.tools.gameprofile.ProfileLoader;
import net.samagames.tools.holograms.Hologram;
import net.samagames.tools.npc.nms.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
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

    private void sendNPC(Player p, CustomNPC npc)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        p.hidePlayer(npc.getBukkitEntity());
        p.showPlayer(npc.getBukkitEntity());
        this.api.getPlugin().getServer().getScheduler().runTaskLater(this.api.getPlugin(), () -> ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)), 60L);
    }

    private void removeNPC(Player p, CustomNPC npc)
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
        final World w = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = new ProfileLoader(skinUUID.toString(), "[NPC] " + entities.size(), skinUUID).loadProfile();

        final CustomNPC npc = new CustomNPC(w, gameProfile, new PlayerInteractManager(w));
        npc.setLocation(location);

        Hologram hologram = new Hologram(hologramLines);
        hologram.generateLines(location.clone().add(0.0D, 1.8D, 0.0D));

        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);

        npc.setHologram(hologram);
        entities.put(npc, hologram);

        if (scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        //Bukkit.getScheduler().runTaskLater(api.getPlugin(), () -> updateForAllNPC(npc), 2L);
        return npc;
    }

    public void removeNPC(String name)
    {
        CustomNPC npc = getNPCEntity(name);

        removeNPC(npc);
    }

    public void removeNPC(CustomNPC npc)
    {
        if (npc != null)
        {
            npc.getHologram().destroy();
            for (Player p : Bukkit.getOnlinePlayers())
            {
                removeNPC(p, npc);
            }
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
        Bukkit.getScheduler().runTaskLater(api.getPlugin(),
                () -> entities.keySet().forEach(customNPC ->
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
