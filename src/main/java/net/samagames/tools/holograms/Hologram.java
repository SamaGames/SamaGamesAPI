package net.samagames.tools.holograms;

import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Hologram
{
    private static final double distance = 0.24D;

    private HashMap<OfflinePlayer, Boolean> receivers;
    private HashMap<Integer, EntityArmorStand> entities;
    private List<String> lines;
    private Location location;
    private BukkitTask taskID;
    private double rangeView = 60;
    private boolean linesChanged = false;

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

        this.linesChanged = true;

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
        EntityArmorStand entity = this.entities.get(line);

        if(entity == null)
            return false;

        Reflection.sendPacket(p, new PacketPlayOutEntityDestroy(entity.getId()));

        return true;
    }

    /**
     * Remove lines to all players
     */
    public void removeLinesForPlayers()
    {
        for (OfflinePlayer offlinePlayer : this.receivers.keySet())
        {
            if (!offlinePlayer.isOnline())
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

        this.lines = new ArrayList<>();
        this.lines.addAll(Arrays.asList(lines));
        this.linesChanged = true;

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

            if(this.linesChanged && inRange)
            {
                this.sendLines(p);
                this.linesChanged = false;
            }
            else if(wasInRange == inRange)
            {
                continue;
            }
            else if(wasInRange)
            {
                this.removeLines(p);
            }
            else
            {
                this.sendLines(p);
            }

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

    private static EntityArmorStand generateEntitiesForLine(Location loc, String text)
    {
        EntityArmorStand entity = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
        entity.setSize(0.00001F, 0.00001F);
        entity.setInvisible(true);
        entity.setNoGravity(true);
        entity.setCustomName(text);
        entity.setCustomNameVisible(true);
        entity.setLocation(loc.getX(), loc.getY() - 2, loc.getZ(), 0, 0);

        return entity;
    }

    private boolean sendPacketForLine(Player p, int line)
    {
        EntityArmorStand entity = this.entities.get(line);

        if(entity == null)
            return false;

        Reflection.sendPacket(p, new PacketPlayOutSpawnEntity(entity, 78));
        Reflection.sendPacket(p, new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true));

        return true;
    }
}
