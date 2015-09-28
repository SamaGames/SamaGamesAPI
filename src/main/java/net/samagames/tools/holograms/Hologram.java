package net.samagames.tools.holograms;

import net.minecraft.server.v1_8_R3.*;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by Geekpower14 on 14/03/2015.
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */

//https://www.youtube.com/watch?v=RiE47vq5WsY
public class Hologram {
    private static final double distance = 0.24;
    public double rangeView = 60;
    protected HashMap<OfflinePlayer, Boolean> receivers = new HashMap<>();
    private List<String> lines = new ArrayList<>();
    private HashMap<Integer, Entity> entities = new HashMap<>();
    private Location location;
    private BukkitTask TaskID;

    public Hologram(String... lines) {
        this.lines.addAll(Arrays.asList(lines));

        TaskID = Bukkit.getScheduler().runTaskTimerAsynchronously(SamaGamesAPI.get().getPlugin(), this::sendLinesForPlayers, 10L, 10L);
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

    public boolean addReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;
        Player p = offlinePlayer.getPlayer();
        boolean inRange = false;

        if(p.getLocation().getWorld() == location.getWorld()
                && p.getLocation().distance(location) <= rangeView)
        {
            inRange = true;
            sendLines(offlinePlayer.getPlayer());
        }

        receivers.put(offlinePlayer, inRange);

        return true;
    }

    public boolean removeReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;

        receivers.remove(offlinePlayer);
        removeLines(offlinePlayer.getPlayer());
        return true;
    }

    public void change(String... lines) {
        removeLinesForPlayers();

        clearEntities();
        clearLines();
        this.lines = Arrays.asList(lines);
        generateLines(this.location);
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void generateLines()
    {
        generateLines(location);
    }

    public void generateLines(Location loc) {
        Location first = loc.clone().add(0, (this.lines.size() / 2) * distance, 0);
        for (int i = 0; i < this.lines.size(); i++)
        {
            entities.put(i, generateEntitiesForLine(first.clone(), this.lines.get(i)));
            first.subtract(0, distance, 0);
        }
        this.location = loc;
    }

    public void sendLinesForPlayers()
    {
        for(OfflinePlayer offlinePlayer : receivers.keySet())
        {
            if(!offlinePlayer.isOnline())
                continue;

            Player p = offlinePlayer.getPlayer();
            boolean wasInRange = receivers.get(offlinePlayer);
            boolean inRange = false;

            if(p.getLocation().getWorld() == location.getWorld()
                    && p.getLocation().distance(location) <= rangeView)
            {
                inRange = true;
            }

            if(wasInRange == inRange)
                continue;

            if(wasInRange && !inRange)
            {
                removeLines(p);
            }

            if(!wasInRange && inRange)
            {
                sendLines(p);
            }
            receivers.put(offlinePlayer, inRange);
        }
    }

    public void sendLines(Player p)
    {
        for (int i = 0; i < this.lines.size(); i++)
        {
            sendPacketForLine(p, i);
        }
    }

    public void removeLines(Player p)
    {
        for (int i = 0; i < this.lines.size(); i++)
        {
            removeLineForPlayer(p, i);
        }
    }

    public boolean removeLineForPlayer(Player p, int line)
    {
        Entity entities = this.entities.get(line);
        if(entities == null)
            return false;

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entities.getId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        return true;
    }

    public void destroy()
    {
        removeLinesForPlayers();

        clearEntities();
        clearLines();

        this.location = null;
    }

    public void fullDestroy()
    {
        destroy();
        receivers.clear();
        TaskID.cancel();
    }

    public void removeLinesForPlayers()
    {
        for(OfflinePlayer offlinePlayer : receivers.keySet())
        {
            if(!offlinePlayer.isOnline())
                continue;

            removeLines(offlinePlayer.getPlayer());
        }
    }

    public void clearEntities()
    {
        entities.clear();
    }

    public void clearLines()
    {
        lines.clear();
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
