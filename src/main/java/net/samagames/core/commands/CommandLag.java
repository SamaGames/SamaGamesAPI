package net.samagames.core.commands;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.samagames.core.APIPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class CommandLag extends AbstractCommand {
	public CommandLag(APIPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] arguments) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "Latence : 0.0 ms");
			return true;
		}

		Player player = (Player) sender;
		int latency = ((CraftPlayer) player).getHandle().ping;

		StringBuilder tps = new StringBuilder(org.bukkit.ChatColor.GOLD + "TPS des dernières 1m, 5m, 15m: ");
		double[] tab;
		int length = (tab = MinecraftServer.getServer().recentTps).length;

		for(int var7 = 0; var7 < length; ++var7) {
			tps.append(this.format(tab[var7]));
			tps.append(", ");
		}

		String server = APIPlugin.getInstance().getServerName();

		player.sendMessage(org.bukkit.ChatColor.GOLD + "====== Informations de Lag ======");
		player.sendMessage(ChatColor.GOLD + "Serveur : " + ChatColor.AQUA + server);
		player.sendMessage(ChatColor.GOLD + "Latence (ping) : " + ChatColor.AQUA + formatLag(latency) + " ms");
		player.sendMessage(ChatColor.GOLD + "Ticks par seconde (20 = Parfait - 0 = Catastrophique)");
		player.sendMessage(tps.toString());
		player.sendMessage(org.bukkit.ChatColor.GOLD + "====== Informations de Lag ======");

		return true;
	}


	private String format(double tps) {
		return (tps > 18.0D? org.bukkit.ChatColor.GREEN:(tps > 16.0D? org.bukkit.ChatColor.YELLOW: org.bukkit.ChatColor.RED)).toString() + (tps > 20.0D?"*":"") + Math.min((double)Math.round(tps * 100.0D) / 100.0D, 20.0D);
	}

	private String formatLag(double lag) {
		return "" + (lag > 200.0 ? org.bukkit.ChatColor.RED : (lag > 120D? org.bukkit.ChatColor.GOLD: (lag > 70D? org.bukkit.ChatColor.YELLOW: org.bukkit.ChatColor.GREEN))).toString() + Math.min((double)Math.round(lag * 100.0D) / 100.0D, 20.0D);
	}
}
