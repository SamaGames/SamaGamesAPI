package net.samagames.core.api.player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.core.APIPlugin;
import net.samagames.permissionsapi.permissions.PermissionUser;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.Date;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
class StarsManager {

	protected Promo currentPromo;
	protected Date promoNextCheck = null;
	protected SamaGamesAPI api;

	public StarsManager() {
		api = APIPlugin.getApi();
	}

	public Multiplier getCurrentMultiplier(UUID joueur) {
		Date current = new Date();
		Multiplier ret = new Multiplier();

		if (promoNextCheck == null || current.after(promoNextCheck)) {
			Jedis jedis = api.getResource();
			String prom = jedis.get("stars:currentpromo"); // On get la promo
			jedis.close();

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
		int multiply = (user != null && user.getProperty("stars-multiplier") != null) ? Integer.decode(user.getProperty("stars-multiplier")) : 0;

		multiply = (multiply < 1) ? 1 : multiply;

		ret.globalAmount *= multiply;
		return ret;
	}

	public TextComponent getCreditMessage(long amount, String reason, Multiplier multiplier) {
		TextComponent gain = getCreditMessage(amount, reason);

		if (multiplier != null)
			for (String multCause : multiplier.infos.keySet()) {
				TextComponent line = new TextComponent(multCause);
				line.setColor(ChatColor.GREEN);
				TextComponent details = new TextComponent(" *" + multiplier.infos.get(multCause));
				details.setColor(ChatColor.AQUA);
				line.addExtra(details);

				TextComponent toAdd = new TextComponent(" [");
				toAdd.setColor(ChatColor.GOLD);
				toAdd.addExtra(line);
				toAdd.addExtra(ChatColor.GOLD + "]");

				gain.addExtra(line);
			}


		return gain;
	}

	public TextComponent getCreditMessage(long amount, String reason) {
		TextComponent gain = getCreditMessage(amount);
		TextComponent rComponent = new TextComponent(" (" + reason + ") ");
		rComponent.setColor(ChatColor.GREEN);
		gain.addExtra(rComponent);
		return gain;
	}

	public TextComponent getCreditMessage(long amount) {
		TextComponent msg = new TextComponent("Vous gagnez ");
		msg.setColor(ChatColor.DARK_AQUA);
		TextComponent gain = new TextComponent(amount + " Étoiles !");
		gain.setColor(ChatColor.AQUA);
		msg.addExtra(gain);
		return msg;
	}
}
