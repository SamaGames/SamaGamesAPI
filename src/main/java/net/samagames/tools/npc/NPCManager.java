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
 * NPC manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class NPCManager
{
    private SamaGamesAPI api;
    private ScheduledExecutorService scheduler;
    private List<NPCEntity> entities;
    private CallBack<NPCEntity> scoreboardRegister;

    /**
     * Constructor
     *
     * @param api SamaGames API instance
     */
    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;
        this.entities = new ArrayList<>();

        this.scheduler = Executors.newScheduledThreadPool(2);
        this.scheduler.scheduleAtFixedRate(this::doCheck, 800, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * Disable NPC handling
     */
    public void disable()
    {
        this.scheduler.shutdown();
    }

    /**
     * Register a NPC
     *
     * @param uuid NPC UUID
     * @param location NPC location
     *
     * @return Entity instance
     */
    public NPCEntity registerNPC(UUID uuid, Location location)
    {
        NPCEntity entity = new NPCEntity(uuid, ("[NPC]" + this.entities.size()), location);
        this.entities.add(entity);

        if(this.scoreboardRegister != null)
            this.scoreboardRegister.done(entity, null);

        return entity;
    }

    /**
     * Add NPCs receiver
     *
     * @param player Player
     */
    public void addReceiver(Player player)
    {
        for(NPCEntity entity : this.entities)
        {
            entity.addReceiver(player.getUniqueId());
            entity.spawnEntity(player);
        }
    }

    /**
     * Remove NPCs receiver
     *
     * @param player Player
     */
    public void removeReceiver(Player player)
    {
        for(NPCEntity entity : this.entities)
        {
            entity.removeReceiver(player.getUniqueId());
            entity.destroyEntity(player);
        }
    }

    /**
     * Set scoreboard handling for the TAB managing
     *
     * @param scoreBoardRegister Callback
     */
    public void setScoreboardRegister(CallBack<NPCEntity> scoreBoardRegister)
    {
        this.scoreboardRegister = scoreBoardRegister;
    }

    /**
     * Get NPC entity by a given name
     *
     * @param name NPC name
     *
     * @return Entity instance
     */
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

    /**
     * Send NPCs to nearby players
     */
    private void doCheck()
    {
        for(NPCEntity entity : entities)
        {
            Collection<Entity> nearbyEntities = entity.getPosition().getWorld().getNearbyEntities(entity.getPosition(), 30, 30, 30);
            List<UUID> idInRange = new ArrayList<>();

            nearbyEntities.stream().filter(entityW -> entityW instanceof Player).forEach(entityW ->
            {
                UUID uniqueId = entityW.getUniqueId();

                if (!entity.isReceiver(uniqueId))
                {
                    entity.addReceiver(uniqueId);
                    entity.spawnEntity((Player) entityW);
                }

                idInRange.add(uniqueId);
            });

            entity.getReceivers().stream().filter(uuid -> !idInRange.contains(uuid)).forEach(uuid ->
            {
                entity.removeReceiver(uuid);
                entity.destroyEntity(Bukkit.getPlayer(uuid));
            });
        }
    }
}
