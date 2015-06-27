package net.samagames.core.commands;

import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandInv extends AbstractCommand
{
    public CommandInv(APIPlugin plugin)
    {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] arguments)
    {
        if (!hasPermission(sender, "inventory.show"))
            return true;

        if(sender instanceof Player)
        {
            if(arguments.length != 0)
            {
                String playerName = arguments[0];
                Player player = Bukkit.getPlayer(playerName);

                if(player != null)
                {
                    Inventory inventory = player.getInventory();
                    ((Player) sender).closeInventory();
                    ((Player) sender).openInventory(inventory);
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Le joueur spécifié n'est pas en ligne !");
                }
            }
            else
            {
                return false;
            }
        }

        return true;
    }
}
