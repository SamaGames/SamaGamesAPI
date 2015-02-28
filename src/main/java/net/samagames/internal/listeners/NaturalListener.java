package net.samagames.internal.listeners;

import net.zyuiop.MasterBundle.MasterBundle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.world.StructureGrowEvent;

/**
 * Created by Geekpower14 on 21/12/2014.
 */
public class NaturalListener implements Listener
{
    public MasterBundle plugin;

    public NaturalListener(MasterBundle plugin)
    {
        this.plugin = plugin;

        /*if (plugin.getConfig().getBoolean("disable-nature", true)) {

        }*/
    }

    @EventHandler
    public void onBlockGrowEvent(BlockGrowEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFadeEvent(BlockFadeEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setNewCurrent(event.getOldCurrent());
        }
    }

    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event)
    {
        if (plugin.getConfig().getBoolean("disable-nature", false))
        {
            event.setCancelled(true);
        }
    }



}
