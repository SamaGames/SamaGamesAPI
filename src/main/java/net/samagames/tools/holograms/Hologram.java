package net.samagames.tools.holograms;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static Class<?> entityArmorStandClass;
    private static Class<?> packetPlayOutEntityDestroyClass;
    private static Class<?> packetPlayOutSpawnEntityClass;
    private static Class<?> packetPlayOutEntityMetadataClass;

    private static Method setSizeMethod;
    private static Method setInvisibleMethod;
    //private static Method setNoGravityMethod;
    private static Method setCustomNameMethod;
    private static Method setCustomNameVisibleMethod;
    private static Method setLocationMethod;
    private static Method getIdMethod;
    private static Method getDataWatcherMethod;

    private HashMap<OfflinePlayer, Boolean> receivers;
    private HashMap<Integer, Object> entities;
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
        Object entity = this.entities.get(line);

        if(entity == null)
            return false;

        try
        {
            Object packet = packetPlayOutEntityDestroyClass.getDeclaredConstructor(int.class).newInstance(getIdMethod.invoke(entity));
            Reflection.sendPacket(p, packet);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }

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

    private static Object generateEntitiesForLine(Location loc, String text)
    {
        try
        {
            Class<?> worldClass = Reflection.getNMSClass("World");
            Object world = Reflection.getHandle(loc.getWorld());
            Object entity = entityArmorStandClass.getDeclaredConstructor(worldClass).newInstance(world);

            setSizeMethod.invoke(entity, 0.00001F, 0.00001F);
            setInvisibleMethod.invoke(entity, true);
            //setNoGravityMethod.invoke(entity, true);
            setCustomNameMethod.invoke(entity, text);
            setCustomNameVisibleMethod.invoke(entity, true);
            setLocationMethod.invoke(entity, loc.getX(), loc.getY() - 2, loc.getZ(), 0, 0);

            return entity;
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private boolean sendPacketForLine(Player p, int line)
    {
        Object entity = this.entities.get(line);

        if(entity == null)
            return false;

        try
        {
            Class<?> packetPlayOutSpawnEntityClass = Reflection.getNMSClass("PacketPlayOutSpawnEntity");
            Class<?> packetPlayOutEntityMetadataClass = Reflection.getNMSClass("PacketPlayOutEntityMetadata");

            Method getDataWatcherMethod = entityArmorStandClass.getMethod("getDataWatcher");

            Object armorStandPacket = packetPlayOutSpawnEntityClass.getDeclaredConstructor(entity.getClass(), int.class).newInstance(entity, 78);
            Object armorStandPacketMeta = packetPlayOutEntityMetadataClass.getDeclaredConstructor(int.class, getDataWatcherMethod.getReturnType(), boolean.class).newInstance(getIdMethod.invoke(entity), getDataWatcherMethod.invoke(entity), true);

            Reflection.sendPacket(p, armorStandPacket);
            Reflection.sendPacket(p, armorStandPacketMeta);
        }
        catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return true;
    }

    static
    {
        try
        {
            entityArmorStandClass = Reflection.getNMSClass("EntityArmorStand");
            packetPlayOutEntityDestroyClass = Reflection.getNMSClass("PacketPlayOutEntityDestroy");
            packetPlayOutSpawnEntityClass = Reflection.getNMSClass("PacketPlayOutSpawnEntity");
            packetPlayOutEntityMetadataClass = Reflection.getNMSClass("PacketPlayOutEntityMetadata");

            getDataWatcherMethod = entityArmorStandClass.getMethod("getDataWatcher");
            setSizeMethod = entityArmorStandClass.getMethod("setSize", float.class, float.class);
            setInvisibleMethod = entityArmorStandClass.getMethod("setInvisible", boolean.class);
            //setNoGravityMethod = entityArmorStandClass.getMethod("setNoGravity", boolean.class);
            setCustomNameMethod = entityArmorStandClass.getMethod("setCustomName", String.class);
            setCustomNameVisibleMethod = entityArmorStandClass.getMethod("setCustomNameVisible", boolean.class);
            setLocationMethod = entityArmorStandClass.getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            getIdMethod = entityArmorStandClass.getMethod("getId");
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }
}
