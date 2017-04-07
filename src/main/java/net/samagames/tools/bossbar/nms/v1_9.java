package net.samagames.tools.bossbar.nms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This is the FakeDragon class for BarAPI.
 * It is based on the code by SoThatsIt.
 * <p>
 * http://forums.bukkit.org/threads/tutorial-utilizing-the-boss-health-bar.158018/page-5#post-2053705
 *
 * @author James Mortemore
 */

public class v1_9 extends FakeDragon {

    private Object dragon;
    private int id;
    private BossBar bar;

    public v1_9(String name, Location loc) {
        super(name, loc);

        try
        {
            Method createBossBarMethod = Bukkit.class.getMethod("createBossBar", String.class, BarColor.class, BarStyle.class, BarFlag[].class);
            bar = (BossBar) createBossBarMethod.invoke(null, name, BarColor.PINK, BarStyle.SOLID, new BarFlag[0]);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public BossBar getBar() {
        return bar;
    }

    @Override
    public Object getSpawnPacket() {
        return null;
    }

    @Override
    public Object getDestroyPacket() {
        return null;
    }

    @Override
    public Object getMetaPacket(Object watcher) {
        return null;
    }

    @Override
    public Object getTeleportPacket(Location loc) {
        return null;
    }

    @Override
    public Object getWatcher() {
        return null;
    }
}