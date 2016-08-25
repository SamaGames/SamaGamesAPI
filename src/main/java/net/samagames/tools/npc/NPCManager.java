package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.server.v1_9_R2.*;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import net.samagames.tools.gameprofile.ProfileLoader;
import net.samagames.tools.holograms.Hologram;
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
import org.bukkit.event.player.*;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

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
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
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
        return createNPC(location, skinUUID, new String[] { "[NPC] " + entities.size() });
    }


    private static final String JSON_SKIN = "{\"timestamp\":%d,\"profileId\":\"%s\",\"profileName\":\"%s\",\"isPublic\":true,\"textures\":{\"SKIN\":{\"url\":\"%s\"}}}";
    private static final String JSON_CAPE = "{\"timestamp\":%d,\"profileId\":\"%s\",\"profileName\":\"%s\",\"isPublic\":true,\"textures\":{\"SKIN\":{\"url\":\"%s\"},\"CAPE\":{\"url\":\"%s\"}}}";

    public CustomNPC createNPC(Location location, String skinUrl, String capeUrl, String[] hologramLines)
    {
        final World w = ((CraftWorld) location.getWorld()).getHandle();
        boolean cape = capeUrl != null && !capeUrl.isEmpty();

        UUID uuid = UUID.randomUUID();
        GameProfile gameProfile = new GameProfile(uuid, "");
        List<Object> list = new ArrayList<>();
        list.add(System.currentTimeMillis());
        list.add(UUIDTypeAdapter.fromUUID(uuid));
        list.add("");
        list.add(skinUrl);
        if (cape)
            list.add(capeUrl);
        gameProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString(String.format(cape ? JSON_CAPE : JSON_SKIN, list.toArray(new Object[list.size()])))));

        final CustomNPC npc = new CustomNPC(w, gameProfile, new PlayerInteractManager(w));
        npc.setLocation(location);

        Hologram hologram = new Hologram(hologramLines);
        hologram.generateLines(location.clone().add(0.0D, 2.0D, 0.0D));

        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);

        entities.put(npc, hologram);

        if (scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        Bukkit.getScheduler().runTaskLater(api.getPlugin(), () -> updateForAllNPC(npc), 2L);

        return npc;
    }

    public CustomNPC createNPC(Location location, UUID skinUUID, String[] hologramLines)
    {
        final World w = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = new ProfileLoader(skinUUID.toString(), "[NPC] " + entities.size(), skinUUID).loadProfile();

        final CustomNPC npc = new CustomNPC(w, gameProfile, new PlayerInteractManager(w));
        npc.setLocation(location);

        Hologram hologram = new Hologram(hologramLines);
        hologram.generateLines(location.clone().add(0.0D, 2.0D, 0.0D));

        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);

        entities.put(npc, hologram);

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
