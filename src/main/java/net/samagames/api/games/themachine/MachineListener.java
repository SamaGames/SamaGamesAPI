package net.samagames.api.games.themachine;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MachineListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getItem().isSimilar(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getLeaveItem()))
            event.getPlayer().kickPlayer(null);
    }
}
