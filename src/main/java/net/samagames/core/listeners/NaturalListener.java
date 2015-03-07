package net.samagames.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.world.StructureGrowEvent;

public class NaturalListener implements Listener {

    @EventHandler
    public void onBlockGrowEvent(BlockGrowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFadeEvent(BlockFadeEvent event) {
		event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
            event.setNewCurrent(event.getOldCurrent());
    }

    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent event) {
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event) {
            event.setCancelled(true);
    }

    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event) {
            event.setCancelled(true);
    }



}
