package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.World;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager  implements Listener{

    public SamaGamesAPI api;

    private ScheduledExecutorService scheduler;

    private List<CustomNPC> entities = new ArrayList<>();

    protected List<OfflinePlayer> receivers = new ArrayList<>();

    private CallBack<CustomNPC> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;
        scheduler = Executors.newScheduledThreadPool(2);

        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    public CustomNPC createNPC(Location location, UUID uuid)
    {
        final World w = ((CraftWorld) location.getWorld()).getHandle();
        final CustomNPC npc = new CustomNPC(w, new GameProfile(uuid, "[NPC]"+entities.size()));
        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entities.add(npc);

        if(scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        return npc;
    }

    public void removeNPC(String name)
    {
        CustomNPC npc = getNPCEntity(name);
        if(npc != null)
            npc.getWorld().removeEntity(npc);

    }

    public void disable()
    {
        scheduler.shutdown();
    }

    public CustomNPC getNPCEntity(String name)
    {
        for(CustomNPC entity : entities)
        {
            if(entity.getName().equals(name))
            {
                return entity;
            }
        }
        return null;
    }

    public void setScoreBoardRegister(CallBack<CustomNPC> scoreBoardRegister) {
        this.scoreBoardRegister = scoreBoardRegister;
    }

    @EventHandler
    public void onPlayerHitNPC(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof CustomNPC && event.getDamager() instanceof Player)
        {
            CustomNPC npc = (CustomNPC) event.getEntity();
            npc.onInteract(false, (Player) event.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteractNPC(PlayerInteractEntityEvent event)
    {
        if(event.getRightClicked() instanceof CustomNPC)
        {
            CustomNPC npc = (CustomNPC) event.getRightClicked();
            npc.onInteract(true, event.getPlayer());
        }
    }
}
