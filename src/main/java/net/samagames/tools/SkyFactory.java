package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * SkyFactory class
 *
 * SkyFactory is a simple class to change the environment of the sky.
 *
 * @author BigTeddy98
 */
public class SkyFactory implements Listener
{
    private static Constructor<?> packetPlayOutRespawn;
    private static Method getHandle;
    private static Field playerConnection;
    private static Method sendPacket;
    private static Field normal;

    static
    {
        try
        {
            packetPlayOutRespawn = getMCClass("PacketPlayOutRespawn").getConstructor(int.class, getMCClass("EnumDifficulty"), getMCClass("WorldType"), net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode.class);
            getHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
            playerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
            sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket", getMCClass("Packet"));
            normal = getMCClass("WorldType").getDeclaredField("NORMAL");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private final JavaPlugin plugin;
    private final Map<String, Environment> worldEnvironments = new HashMap<>();

    public SkyFactory(JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    /**
     * Set a given environment "design" to a given
     * world
     *
     * @param w World
     * @param env Environment
     */
    public void setDimension(World w, Environment env)
    {
        this.worldEnvironments.put(w.getName(), env);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException
    {
        Player p = event.getPlayer();

        if (this.worldEnvironments.containsKey(p.getWorld().getName()))
        {
            Object nms_entity = getHandle.invoke(p);
            Object nms_connection = playerConnection.get(nms_entity);

            sendPacket.invoke(nms_connection, getPacket(p));
        }
    }

    @EventHandler
    private void onRespawn(final PlayerRespawnEvent event)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Player p = event.getPlayer();

                    if (worldEnvironments.containsKey(p.getWorld().getName()))
                    {
                        Object nms_entity = getHandle.invoke(p);
                        Object nms_connection = playerConnection.get(nms_entity);
                        sendPacket.invoke(nms_connection, getPacket(p));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskLater(this.plugin, 1);
    }

    private Object getPacket(Player p) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
    {
        World w = p.getWorld();
        return packetPlayOutRespawn.newInstance(getID(this.worldEnvironments.get(w.getName())), this.getDifficulty(w), this.getLevel(), this.getGameMode(p));
    }

    private int getID(Environment env)
    {
        if (env == Environment.NETHER)
            return -1;
        else if (env == Environment.NORMAL)
            return 0;
        else if (env == Environment.THE_END)
            return 1;
        else
            return -1;
    }

    private Object getDifficulty(World w) throws ClassNotFoundException
    {
        for (Object dif : getMCClass("EnumDifficulty").getEnumConstants())
            if (dif.toString().equalsIgnoreCase(w.getDifficulty().toString()))
                return dif;

        return null;
    }

    private Object getGameMode(Player p) throws ClassNotFoundException
    {
        for (Object dif : net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode.values())
            if (dif.toString().equalsIgnoreCase(p.getGameMode().toString()))
                return dif;

        return null;
    }

    private Object getLevel() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        return normal.get(null);
    }

    private static Class<?> getMCClass(String name) throws ClassNotFoundException
    {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "net.minecraft.server." + version + name;

        return Class.forName(className);
    }

    private static Class<?> getCraftClass(String name) throws ClassNotFoundException
    {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "org.bukkit.craftbukkit." + version + name;

        return Class.forName(className);
    }
}
