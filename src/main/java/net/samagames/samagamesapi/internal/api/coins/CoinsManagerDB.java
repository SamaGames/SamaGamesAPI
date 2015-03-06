package net.samagames.samagamesapi.internal.api.coins;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.samagamesapi.api.SamaGamesAPI;
import net.samagames.samagamesapi.internal.APIPlugin;
import net.samagames.permissionsapi.permissions.PermissionUser;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class CoinsManagerDB extends CoinsManagerBase {

	protected Promo currentPromo;
	protected Date promoNextCheck = null;
	protected SamaGamesAPI api;

	public CoinsManagerDB(APIPlugin plugin) {
		super(plugin);
		api = APIPlugin.getApi();
	}

	protected Multiplier getCurrentMultiplier(UUID joueur) {
		Date current = new Date();
		Multiplier ret = new Multiplier();

		if (promoNextCheck == null || current.after(promoNextCheck)) {
			String prom = api.getDatabase().fastGet("coins:currentpromo"); // On get la promo
			if (prom == null) {
				currentPromo = null;
			} else {
				currentPromo = new Promo(prom);
			}
			promoNextCheck = new Date();
			promoNextCheck.setTime(promoNextCheck.getTime() + (60 * 1000));
		}

		if (currentPromo != null && current.before(currentPromo.end)) {
			ret.globalAmount = currentPromo.multiply;
			ret.infos.put(currentPromo.message, currentPromo.multiply);
		}

		PermissionUser user = PermissionsBukkit.getApi().getUser(joueur);
		int multiply = (user != null && user.getProperty("multiplier") != null) ? Integer.decode(user.getProperty("multiplier")) : 0;

		multiply = (multiply < 1) ? 1 : multiply;

		ret.globalAmount += multiply;
		return ret;
	}

	@Override
	public long creditPlayerSynchronized(final UUID player, int amount, final String reason, boolean applyMultiplier) {
		final ArrayList<TextComponent> lines = new ArrayList<>();

		// Application de la promotion
		if (applyMultiplier) {
			Multiplier m = getCurrentMultiplier(player);
			amount *= m.globalAmount;

			for (String multCause : m.infos.keySet()) {
				TextComponent line = new TextComponent(multCause);
				line.setColor(ChatColor.GREEN);
				TextComponent details = new TextComponent(" *" + m.infos.get(multCause));
				details.setColor(ChatColor.AQUA);
				line.addExtra(details);

				TextComponent toAdd = new TextComponent(" [");
				toAdd.setColor(ChatColor.GOLD);
				toAdd.addExtra(line);
				toAdd.addExtra(ChatColor.GOLD + "]");

				lines.add(toAdd);
			}
		}

		ShardedJedis jedis = api.getResource();
		long amountAfter = jedis.incrBy("coins:" + player, amount); // Conversion en String la plus efficace au monde.
		jedis.close();

		if (reason != null) {
			final int finalAmount = amount;
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

				public void run() {
					Player joueur = Bukkit.getPlayer(player);
					if (joueur != null) {
						TextComponent gain = new TextComponent("+" + finalAmount + " Coins ");
						gain.setColor(ChatColor.GOLD);
						gain.addExtra(new ComponentBuilder("(" + reason + ")").color(ChatColor.AQUA).create()[0]);

						for (TextComponent additionnal : lines) {
							gain.addExtra(additionnal);
						}

						joueur.sendMessage(gain.toLegacyText());
					}
				}
			});
		}
		return amountAfter;
	}

	@Override
	public long withdrawPlayerSynchronized(UUID player, int amount) {
		ShardedJedis jedis = api.getResource();
		long left = jedis.decrBy("coins:" + player, amount);
		jedis.close();

		return left;
	}

	@Override
	public boolean canPay(UUID player, int amount) {
		return getAmount(player) > amount;
	}

	@Override
	public int getAmount(UUID player) {
		String value = api.getDatabase().fastGet("coins:" + player);
		return (value == null) ? 0 : Integer.parseInt(value);
	}
}
