package net.samagames.tools.holograms;

import net.minecraft.server.v1_9_R1.*;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Hologram object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Hologram
{
    private static final double distance = 0.24D;

    private HashMap<OfflinePlayer, Boolean> receivers;
    private HashMap<Integer, Entity> entities;
    private List<String> lines;
    private Location location;
    private BukkitTask taskID;
    private double rangeView = 60;

    /**
     * Constructor
     *
     * @param lines Hologram's lines
     */
    public Hologram(String... lines)
    {
        this.receivers = new HashMap<>();
        this.entities = new HashMap<>();

        this.lines = new ArrayList<>();
        this.lines.addAll(Arrays.asList(lines));

        this.taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(SamaGamesAPI.get().getPlugin(), this::sendLinesForPlayers, 10L, 10L);
    }

    /**
     * Add hologram's receiver
     *
     * @param offlinePlayer Player
     *
     * @return {@code true} is success
     */
    public boolean addReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;

        Player p = offlinePlayer.getPlayer();
        boolean inRange = false;

        if(p.getLocation().getWorld() == this.location.getWorld() && p.getLocation().distance(this.location) <= this.rangeView)
        {
            inRange = true;
            this.sendLines(offlinePlayer.getPlayer());
        }

        this.receivers.put(offlinePlayer, inRange);

        return true;
    }

    /**
     * Remove hologram's receiver
     *
     * @param offlinePlayer Player
     *
     * @return {@code true} is success
     */
    public boolean removeReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;

        this.receivers.remove(offlinePlayer);
        this.removeLines(offlinePlayer.getPlayer());

        return true;
    }

    /**
     * Remove a given line to a given player
     *
     * @param p Player
     * @param line Line number
     *
     * @return {@code true} is success
     */
    public boolean removeLineForPlayer(Player p, int line)
    {
        Entity entities = this.entities.get(line);

        if(entities == null)
            return false;

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entities.getId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        return true;
    }

    /**
     * Remove lines to all players
     */
    public void removeLinesForPlayers()
    {
        for(OfflinePlayer offlinePlayer : this.receivers.keySet())
        {
            if(!offlinePlayer.isOnline())
                continue;

            this.removeLines(offlinePlayer.getPlayer());
        }
    }

    /**
     * Destroy the hologram
     */
    public void destroy()
    {
        this.removeLinesForPlayers();

        this.clearEntities();
        this.clearLines();

        this.location = null;
    }

    /**
     * Destroy the hologram, it can't be
     * used anymore
     */
    public void fullDestroy()
    {
        this.destroy();
        this.receivers.clear();
        this.taskID.cancel();
    }

    /**
     * Update hologram's lines
     *
     * @param lines Hologram's lines
     */
    public void change(String... lines)
    {
        this.removeLinesForPlayers();

        this.clearEntities();
        this.clearLines();
        this.lines = Arrays.asList(lines);
        this.generateLines(this.location);
    }

    /**
     * Set hologram location
     *
     * @param location Location
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Generate hologram in world
     */
    public void generateLines()
    {
        this.generateLines(this.location);
    }

    /**
     * Generate hologram in world at the given
     * location
     *
     * @param loc Hologram's location
     */
    public void generateLines(Location loc)
    {
        Location first = loc.clone().add(0, (this.lines.size() / 2) * distance, 0);

        for (int i = 0; i < this.lines.size(); i++)
        {
            this.entities.put(i, generateEntitiesForLine(first.clone(), this.lines.get(i)));
            first.subtract(0, distance, 0);
        }

        this.location = loc;
    }

    /**
     * Send hologram's lines to all players
     */
    public void sendLinesForPlayers()
    {
        for(OfflinePlayer offlinePlayer : this.receivers.keySet())
        {
            if(!offlinePlayer.isOnline())
                continue;

            Player p = offlinePlayer.getPlayer();
            boolean wasInRange = this.receivers.get(offlinePlayer);
            boolean inRange = false;

            if(p.getLocation().getWorld() == this.location.getWorld() && p.getLocation().distance(this.location) <= this.rangeView)
                inRange = true;

            if(wasInRange == inRange)
                continue;

            if(wasInRange && !inRange)
                this.removeLines(p);

            if(!wasInRange && inRange)
                this.sendLines(p);

            this.receivers.put(offlinePlayer, inRange);
        }
    }

    /**
     * Send hologram's lines to a given player
     *
     * @param p Player
     */
    public void sendLines(Player p)
    {
        for (int i = 0; i < this.lines.size(); i++)
            this.sendPacketForLine(p, i);
    }

    /**
     * Remove hologram's lines to a given player
     *
     * @param p Player
     */
    public void removeLines(Player p)
    {
        for (int i = 0; i < this.lines.size(); i++)
            this.removeLineForPlayer(p, i);
    }

    /**
     * Clear hologram's entities who permits
     * all the system to work
     */
    public void clearEntities()
    {
        this.entities.clear();
    }

    /**
     * Clear hologram's lines
     */
    public void clearLines()
    {
        this.lines.clear();
    }

    /**
     * Get hologram location
     *
     * @return Location
     */
    public Location getLocation()
    {
        return this.location;
    }

    private static Entity generateEntitiesForLine(Location loc, String text)
    {
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand armorStand = new EntityArmorStand(world);
        armorStand.setSize(0.00001F, 0.00001F);
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        armorStand.setLocation(loc.getX(), loc.getY() - 2, loc.getZ(), 0, 0);

        return armorStand;
    }

    private boolean sendPacketForLine(Player p, int line)
    {
        Entity entities = this.entities.get(line);

        if(entities == null)
            return false;

        EntityArmorStand armorStand = (EntityArmorStand) entities;

        PacketPlayOutSpawnEntity armorStand_packet = new PacketPlayOutSpawnEntity(armorStand, 78);
        PacketPlayOutEntityMetadata armorStand_packemeta = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true);

        EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
        nmsPlayer.playerConnection.sendPacket(armorStand_packet);
        nmsPlayer.playerConnection.sendPacket(armorStand_packemeta);

        return true;
    }
}
