package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.gui.AbstractGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SpectatorListener implements Listener
{
    private Game game;

    public SpectatorListener(Game game)
    {
        this.game = game;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(this.game.isSpectator(event.getPlayer()))
            event.setCancelled(true);

        if(!this.game.isGameStarted() || (this.game.isGameStarted() && this.game.isSpectator(event.getPlayer())))
        {
            if (event.getItem() != null)
            {
                if (event.getItem().getType() == Material.WOOD_DOOR)
                {
                    event.setCancelled(true);

                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);

                    try
                    {
                        out.writeUTF("Connect");
                        out.writeUTF("lobby");
                    }
                    catch (IOException e)
                    {
                        Bukkit.getLogger().info("You'll never see me!");
                    }

                    event.getPlayer().sendPluginMessage(SamaGamesAPI.get().getPlugin(), "BungeeCord", b.toByteArray());

                    return;
                }

                if (SamaGamesAPI.get().getGameManager().getGame().isSpectator(event.getPlayer()))
                    if (event.getItem().isSimilar(SamaGamesAPI.get().getGameManager().getGame().getPlayerTracker()))
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
                event.setCancelled(true);

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

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event)
    {
        if (this.game.isGameStarted() && this.game.isSpectator((Player) event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent event)
    {
        if (!event.isBuildable())
        {
            Location blockL = event.getBlock().getLocation();

            boolean allowed = false;

            for (Player target : Bukkit.getOnlinePlayers())
            {
                if (target.getWorld().equals(event.getBlock().getWorld()))
                {
                    Location playerL = target.getLocation();

                    if (playerL.getX() > blockL.getBlockX() - 1 && playerL.getX() < blockL.getBlockX() + 1)
                    {
                        if (playerL.getZ() > blockL.getBlockZ() - 1 && playerL.getZ() < blockL.getBlockZ() + 1)
                        {
                            if (playerL.getY() > blockL.getBlockY() - 2 && playerL.getY() < blockL.getBlockY() + 1)
                            {
                                if(this.game.isSpectator(target))
                                {
                                    allowed = true;
                                }
                                else
                                {
                                    allowed = false;
                                    break;
                                }
                            }
                        }
                    }

                }
            }

            event.setBuildable(allowed);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Projectile && !(e.getDamager() instanceof ThrownPotion) && e.getEntity() instanceof Player && this.game.isSpectator((Player) e.getEntity()))
        {
            e.setCancelled(true);

            final Player spectatorInvolved = (Player) e.getEntity();
            final boolean wasFlying = spectatorInvolved.isFlying();
            final Location initialSpectatorLocation = spectatorInvolved.getLocation();

            final Vector initialProjectileVelocity = e.getDamager().getVelocity();
            final Location initialProjectileLocation = e.getDamager().getLocation();

            spectatorInvolved.setFlying(true);
            spectatorInvolved.teleport(initialSpectatorLocation.clone().add(0, 6, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);

            Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
            {
                e.getDamager().teleport(initialProjectileLocation);
                e.getDamager().setVelocity(initialProjectileVelocity);
            }, 1L);

            Bukkit.getScheduler().runTaskLater(SamaGamesAPI.get().getPlugin(), () ->
            {
                spectatorInvolved.teleport(new Location(initialSpectatorLocation.getWorld(), initialSpectatorLocation.getX(), initialSpectatorLocation.getY(), initialSpectatorLocation.getZ(), spectatorInvolved.getLocation().getYaw(), spectatorInvolved.getLocation().getPitch()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                spectatorInvolved.setFlying(wasFlying);
            }, 5L);
        }
    }

    @EventHandler
    public void onPlayerPickItem(PlayerPickupItemEvent event)
    {
        if(!this.game.isGameStarted() || (this.game.isGameStarted() && this.game.isSpectator(event.getPlayer())))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event)
    {
        if(event.getTarget() instanceof Player)
            if(!this.game.isGameStarted() || (this.game.isGameStarted() && this.game.isSpectator((Player) event.getTarget())))
                event.setCancelled(true);
    }
}
