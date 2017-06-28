package net.samagames.tools;

import net.minecraft.server.v1_12_R1.EnumDifficulty;
import net.minecraft.server.v1_12_R1.EnumGamemode;
import net.minecraft.server.v1_12_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_12_R1.WorldType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
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
            Reflection.sendPacket(p, getPacket(p));
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
                        Reflection.sendPacket(p, getPacket(p));
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
        return new PacketPlayOutRespawn(getID(this.worldEnvironments.get(w.getName())), this.getDifficulty(w), WorldType.NORMAL, this.getGameMode(p));
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

    private EnumDifficulty getDifficulty(World w) throws ClassNotFoundException
    {
        for (EnumDifficulty gamemode : EnumDifficulty.values())
            if (gamemode.name().equals(w.getDifficulty().name()))
                return gamemode;

        return null;
    }

    private EnumGamemode getGameMode(Player p) throws ClassNotFoundException
    {
        for (EnumGamemode gamemode : EnumGamemode.values())
            if (gamemode.name().equals(p.getGameMode().name()))
                return gamemode;

        return null;
    }
}
