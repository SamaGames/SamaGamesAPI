package net.samagames.api.games.themachine.items;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.api.games.GamePlayer;
import net.samagames.api.gui.AbstractGui;
import net.samagames.tools.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class PlayerTracker extends ItemStack implements IPlayerRunnable
{
    private final Game game;

    public PlayerTracker(Game game)
    {
        super(Material.COMPASS, 1);

        this.game = game;
    }

    @Override
    public void run(Player player)
    {
        AbstractGui gui =  new AbstractGui()
        {
            @Override
            public void display(Player player)
            {
                this.inventory = Bukkit.getServer().createInventory(null, 45, "Téléporteur");

                int slot = 0;

                for(Object inGamePlayerObject : game.getInGamePlayers().values())
                {
                    GamePlayer inGamePlayer = (GamePlayer) inGamePlayerObject;

                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                    headMeta.setOwner(inGamePlayer.getPlayerIfOnline().getName());
                    head.setItemMeta(headMeta);

                    this.setSlotData(PlayerUtils.getFullyFormattedPlayerName(inGamePlayer.getUUID()), head, slot, null, inGamePlayer.getPlayerIfOnline().getUniqueId().toString());

                    slot++;
                }

                this.setSlotData(ChatColor.GREEN + "Fermer", Material.EMERALD, this.inventory.getSize() - 3, null, "close");

                player.openInventory(this.inventory);
            }

            @Override
            public void onClick(Player player, ItemStack stack, String action, ClickType clickType)
            {
                if(action.equals("close"))
                {
                    SamaGamesAPI.get().getGameManager().getGameGuiManager().closeGui(player);
                }
                else
                {
                    Player inGamePlayer = Bukkit.getPlayer(UUID.fromString(action));

                    if(inGamePlayer != null)
                        player.teleport(inGamePlayer);
                }
            }
        };

        SamaGamesAPI.get().getGameManager().getGameGuiManager().openGui(player, gui);
    }
}
