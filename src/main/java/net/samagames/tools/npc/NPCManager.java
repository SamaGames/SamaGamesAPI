package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import net.samagames.tools.Reflection;
import net.samagames.tools.gameprofile.ProfileLoader;
import net.samagames.tools.holograms.Hologram;
import net.samagames.tools.npc.nms.ICustomNPC;
import net.samagames.tools.npc.nms.compat.v1_8_R3.CustomNPC18;
import net.samagames.tools.npc.nms.compat.v1_9_R2.CustomNPC19;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager implements Listener {

    public SamaGamesAPI api;

    private Map<ICustomNPC, Hologram> entities = new HashMap<>();

    private CallBack<ICustomNPC> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;

        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    private void updateForAllNPC(ICustomNPC npc)
    {
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        /*for(Player p : players)
        {
            sendNPC(p, npc);
        }*/
    }

    public void sendNPC(Player p, ICustomNPC npc)
    {
        try
        {
            Class<?> entityClass = Reflection.getNMSClass("Entity");
            Class<?> packetPlayOutPlayerInfoClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo");
            Class<?> enumPlayerInfoActionClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

            Object packet = packetPlayOutPlayerInfoClass.getDeclaredConstructor(enumPlayerInfoActionClass, entityClass).newInstance(enumPlayerInfoActionClass.getField("ADD_PLAYER").get(null), npc);
            Reflection.sendPacket(p, packet);

            p.hidePlayer(npc.getBukkitEntity());
            p.showPlayer(npc.getBukkitEntity());

            this.api.getPlugin().getServer().getScheduler().runTaskLater(this.api.getPlugin(), () ->
            {
                try
                {
                    Object pa = packetPlayOutPlayerInfoClass.getDeclaredConstructor(enumPlayerInfoActionClass, entityClass).newInstance(enumPlayerInfoActionClass.getField("REMOVE_PLAYER").get(null), npc);
                    Reflection.sendPacket(p, pa);
                }
                catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }, 60L);
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public void removeNPC(Player p, ICustomNPC npc)
    {
        p.hidePlayer(npc.getBukkitEntity());
    }

    /**
     * Need to be called async
     * @param location
     * @param skinUUID
     * @return
     */
    public ICustomNPC createNPC(Location location, UUID skinUUID)
    {
        return createNPC(location, skinUUID, new String[] { "[NPC] " + entities.size() });
    }

    public ICustomNPC createNPC(Location location, UUID skinUUID, String[] hologramLines)
    {
        return createNPC(location, skinUUID, hologramLines, true);
    }

    public ICustomNPC createNPC(Location location, UUID skinUUID, String[] hologramLines, boolean showByDefault)
    {
        Object w = Reflection.getHandle(location.getWorld());
        GameProfile gameProfile = new ProfileLoader(skinUUID.toString(), "[NPC] " + entities.size(), skinUUID).loadProfile();

        ICustomNPC npc;

        if (Reflection.PackageType.getServerVersion().equals("v1_8_R3"))
            npc = new CustomNPC18(w, gameProfile);
        else if (Reflection.PackageType.getServerVersion().equals("v1_9_R2"))
            npc = new CustomNPC19(w, gameProfile);
        else
            throw new UnsupportedOperationException("You can't use this API with your Minecraft version.");

        npc.setLocation(location);

        Hologram hologram = null;

        if (hologramLines != null)
        {
            hologram = new Hologram(hologramLines);
            hologram.generateLines(location.clone().add(0.0D, 1.8D, 0.0D));
        }

        try
        {
            Class<?> entityClass = Reflection.getNMSClass("Entity");
            Method addEntityMethod = w.getClass().getMethod("addEntity", entityClass, CreatureSpawnEvent.SpawnReason.class);

            addEntityMethod.invoke(w, npc, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

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
        ICustomNPC npc = getNPCEntity(name);
        removeNPC(npc);
    }

    public void removeNPC(ICustomNPC npc)
    {
        if (npc != null)
        {
            if (npc.getHologram() != null)
                npc.getHologram().destroy();

            for (Player p : Bukkit.getOnlinePlayers())
            {
                removeNPC(p, npc);
            }

            try
            {
                Class<?> entityClass = Reflection.getNMSClass("Entity");
                Object world = npc.getWorld();
                Method removeEntityMethod = world.getClass().getMethod("removeEntity", entityClass);
                removeEntityMethod.invoke(world, npc);
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }

    public ICustomNPC getNPCEntity(String name)
    {
        for (ICustomNPC entity : entities.keySet())
            if (entity.getName().equals(name))
                return entity;

        return null;
    }

    public void setScoreBoardRegister(CallBack<ICustomNPC> scoreBoardRegister) {
        this.scoreBoardRegister = scoreBoardRegister;
    }

    @EventHandler
    public void onPlayerHitNPC(EntityDamageByEntityEvent event)
    {
        if (Reflection.getHandle(event.getEntity()) instanceof ICustomNPC && event.getDamager() instanceof Player)
        {
            ICustomNPC npc = (ICustomNPC) Reflection.getHandle(event.getEntity());
            npc.onInteract(false, (Player) event.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteractNPC(PlayerInteractEntityEvent event)
    {
        if (Reflection.getHandle(event.getRightClicked()) instanceof ICustomNPC)
        {
            ICustomNPC npc = (ICustomNPC) Reflection.getHandle(event.getRightClicked());
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
