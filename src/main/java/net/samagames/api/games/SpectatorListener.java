package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.gui.AbstractGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpectatorListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getItem() != null)
        {
            if(event.getItem().isSimilar(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getLeaveItem()))
            {
                event.getPlayer().kickPlayer(null);
                return;
            }

            if(SamaGamesAPI.get().getGameManager().getGame().isSpectator(event.getPlayer()))
            {
                if(event.getItem().isSimilar(SamaGamesAPI.get().getGameManager().getGame().getPlayerTracker()))
                    SamaGamesAPI.get().getGameManager().getGame().getPlayerTracker().run(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onIntenvoryClick(InventoryClickEvent event)
    {
        if(event.getWhoClicked() instanceof Player)
        {
            Player player = (Player) event.getWhoClicked();

            if(event.getView().getType() == InventoryType.PLAYER)
            {
                event.setCancelled(true);
            }

            if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null)
            {
                AbstractGui gui = SamaGamesAPI.get().getGameManager().getGameGuiManager().getPlayerGui(event.getWhoClicked());

                if (gui != null)
                {
                    String action = gui.getAction(event.getSlot());

                    if (action != null)
                        gui.onClick(player, event.getCurrentItem(), action, event.getClick());

                    event.setCancelled(true);
                }
            }
        }
    }
}
