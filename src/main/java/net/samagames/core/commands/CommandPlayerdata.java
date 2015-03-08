package net.samagames.core.commands;

import com.mysql.jdbc.StringUtils;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerData;
import net.samagames.core.APIPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class CommandPlayerdata extends AbstractCommand {

	public CommandPlayerdata(APIPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] arguments) {
		if (!hasPermission(sender, "playerdata.show"))
			return true;

		if (arguments.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage : /playerdata <pseudo>");
			return true;
		}

		if (arguments.length >= 3 && arguments[0].equalsIgnoreCase("set")) {
			if (!hasPermission(sender, "playerdata.set"))
				return true;

			final String playerName = arguments[1];
			final String key = arguments[2];
			final String value = Strings.join(Arrays.copyOfRange(arguments, 3, arguments.length), " ");

			new Thread(() -> {
				UUID playerId = SamaGamesAPI.get().getUUIDTranslator().getUUID(playerName, true);
				PlayerData data = SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId);
				data.set(key, value);
				sender.sendMessage(ChatColor.YELLOW + "Données modifiées.");
			}, "CommandPlayerDataSet").start();
			return true;
		}
		final String playerName = arguments[0];
			new Thread(() -> {
				UUID playerId = SamaGamesAPI.get().getUUIDTranslator().getUUID(playerName, true);
				PlayerData data = SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId);
				sender.sendMessage(ChatColor.YELLOW + "Data pour " + ChatColor.GREEN + playerName + ChatColor.YELLOW + " / " + ChatColor.AQUA + playerId);
				for (Map.Entry<String, String> entry : data.getValues().entrySet()) {
					sender.sendMessage(ChatColor.YELLOW + " - " + entry.getKey() + " : " + entry.getValue());
				}
			}, "CommandPlayerData").start();

		return true;
	}
}
