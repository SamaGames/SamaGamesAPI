package net.samagames.tools.npc;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager {

    public SamaGamesAPI api;

    private ScheduledExecutorService scheduler;

    private List<NPCEntity> entities = new ArrayList<>();

    protected List<OfflinePlayer> receivers = new ArrayList<>();

    private CallBack<NPCEntity> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;
        init();
    }

    public void init()
    {
        scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(() -> doCheck(), 800, 500, TimeUnit.MILLISECONDS);
    }

    public void disable()
    {
        scheduler.shutdown();
    }

    public NPCEntity registerNPC(UUID uuid, Location location)
    {
        NPCEntity entity = new NPCEntity(uuid, ("[NPC]"+entities.size()), location);
        entities.add(entity);
        if(scoreBoardRegister != null)
            scoreBoardRegister.done(entity, null);

        return entity;
    }

    public NPCEntity getNPCEntity(String name)
    {
        for(NPCEntity entity : entities)
        {
            if(entity.getName().equals(name))
            {
                return entity;
            }
        }
        return null;
    }

    protected void doCheck()
    {
        for(NPCEntity entity : entities)
        {
            Collection<Entity> nearbyEntities = entity.getPosition().getWorld().getNearbyEntities(entity.getPosition(), 30, 30, 30);
            List<UUID> idInRange = new ArrayList<>();
            //Receivers in range
            for(Entity entityW : nearbyEntities)
            {
                if(entityW instanceof Player)
                {
                    UUID uniqueId = entityW.getUniqueId();
                    //New receiver so add it
                    if(!entity.isReceiver(uniqueId))
                    {
                        entity.addReceiver(uniqueId);
                        entity.spawnEntity((Player) entityW);
                    }
                    idInRange.add(uniqueId);
                }
            }
            //Receiver no longer in range
            for(UUID uuid : entity.getReceivers())
            {
                //No longer receiver so remove it
                if(!idInRange.contains(uuid))
                {
                    entity.removeReceiver(uuid);
                    entity.destroyEntity(Bukkit.getPlayer(uuid));
                }
            }
        }
    }

    public void addReceiver(Player player)
    {
        receivers.add(player);
        for(NPCEntity entity : entities)
        {
            entity.addReceiver(player.getUniqueId());
            entity.firstSpawn(player);
        }
    }

    public void removeReceiver(Player player)
    {
        receivers.remove(player);
        for(NPCEntity entity : entities)
        {
            entity.removeReceiver(player.getUniqueId());
            entity.destroyEntity(player);
        }
    }

    public void setScoreBoardRegister(CallBack<NPCEntity> scoreBoardRegister) {
        this.scoreBoardRegister = scoreBoardRegister;
    }
}
