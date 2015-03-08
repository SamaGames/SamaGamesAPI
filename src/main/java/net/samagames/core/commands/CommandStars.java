package net.samagames.core.commands;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerData;
import net.samagames.core.APIPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class CommandStars extends AbstractCommand {

	public CommandStars(APIPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] arguments) {
		if (arguments.length == 0) {
			if (sender instanceof Player) {
				new Thread(() -> {
					Player player = (Player) sender;
					PlayerData data = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
					if (data != null) {
						player.sendMessage(ChatColor.GOLD + "Vous avez actuellement " + ChatColor.GREEN + data.getStars() + " Étoiles");
					} else {
						player.sendMessage(ChatColor.RED + "Une erreur inconnue s'est produite.");
					}
				}, "CommandStarsGet").start();
			} else {
				sender.sendMessage(ChatColor.RED + "Une console ne jouit pas de compte bancaire.");
			}

			return true;
		}

		String operation = arguments[0];
		if (operation.equalsIgnoreCase("get")) {
			if (arguments.length < 2)
				return false;

			if (!hasPermission(sender, "stars.getother"))
				return true;

			final String playerName = arguments[1];
			new Thread(() -> {
				UUID playerId = SamaGamesAPI.get().getUUIDTranslator().getUUID(playerName, true);
				PlayerData data = SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId);

				if (data != null) {
					sender.sendMessage(ChatColor.GOLD + "Le joueur a " + ChatColor.GREEN + data.getStars() + " Étoiles");
				} else {
					sender.sendMessage(ChatColor.RED + "Une erreur inconnue s'est produite.");
				}
			}, "CommandStarsGetOther").start();

			return true;
		} else if (operation.equalsIgnoreCase("credit")) {
			if (arguments.length < 3)
				return false;

			if (!hasPermission(sender, "stars.credit"))
				return true;

			final String playerName = arguments[1];
			final String amount = arguments[2];
			new Thread(() -> {
				UUID playerId = SamaGamesAPI.get().getUUIDTranslator().getUUID(playerName, true);
				PlayerData data = SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId);

				try {
					long amt = Long.valueOf(amount);
					data.creditStars(amt, "Cadeau du staff :p", false, (newAmount, difference, error) -> sender.sendMessage(ChatColor.GOLD + "Le joueur a bien reçu " + difference + " étoiles. Il en a maintenant " + newAmount));
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Format de nombre non valide.");
				}
			}, "CommandStarsCredit").start();

			return true;
		} else if (operation.equalsIgnoreCase("withdraw")) {
			if (arguments.length < 3)
				return false;

			if (!hasPermission(sender, "stars.withdraw"))
				return true;

			final String playerName = arguments[1];
			final String amount = arguments[2];

			new Thread(() -> {
				UUID playerId = SamaGamesAPI.get().getUUIDTranslator().getUUID(playerName, true);
				PlayerData data = SamaGamesAPI.get().getPlayerManager().getPlayerData(playerId);

				try {
					long amt = Long.valueOf(amount);
					data.withdrawStars(amt, (newAmount, difference, error) -> sender.sendMessage(ChatColor.GOLD + "Le joueur a bien perdu " + difference + " étoiles. Il en a maintenant " + newAmount));
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Format de nombre non valide.");
				}
			}, "CommandStarsWithdraw").start();

			return true;
		}

		return false;
	}
}
