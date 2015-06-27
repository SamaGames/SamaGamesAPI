package net.samagames.core.commands;

import net.samagames.core.APIPlugin;
import net.samagames.permissionsapi.PermissionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by zyuiop on 27/08/14.
 */
public class CommandBukkitrefresh implements CommandExecutor {

    protected PermissionsAPI api;

    public CommandBukkitrefresh(PermissionsAPI api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                api.getManager().refresh();
                commandSender.sendMessage(ChatColor.GREEN + "Les permissions locales ont été raffraichies !");
            }
        });
        return true;
    }
}
