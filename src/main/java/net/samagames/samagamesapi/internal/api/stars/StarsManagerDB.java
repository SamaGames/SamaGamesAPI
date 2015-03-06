package net.samagames.samagamesapi.internal.api.stars;

import net.md_5.bungee.api.ChatColor;
import net.samagames.samagamesapi.api.SamaGamesAPI;
import net.samagames.samagamesapi.internal.APIPlugin;
import net.samagames.permissionsapi.permissions.PermissionUser;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.ShardedJedis;

import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class StarsManagerDB extends StarsManagerBase {

	protected SamaGamesAPI api;

	public StarsManagerDB(APIPlugin plugin) {
		super(plugin);
		api = APIPlugin.getApi();
	}

	protected int getCurrentMultiplier(UUID joueur) {
		PermissionUser user = PermissionsBukkit.getApi().getUser(joueur);
		int multiply = (user != null && user.getProperty("starsmultiplier") != null) ? Integer.decode(user.getProperty("starsmultiplier")) : 0;

		multiply = (multiply < 1) ? 1 : multiply;

		return multiply;
	}

	@Override
	public long creditPlayerSynchronized(final UUID player, int amount, final String reason, boolean applyMultiplier) {
		// Application de la promotion
		if (applyMultiplier) {
			amount *= getCurrentMultiplier(player);
		}

		ShardedJedis jedis = api.getResource();
		long amountAfter = jedis.incrBy("stars:" + player, amount); // Conversion en String la plus efficace au monde.
		jedis.close();

		if (reason != null) {
			final int finalAmount = amount;
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

				public void run() {
					Player joueur = Bukkit.getPlayer(player);
					if (joueur != null) {
						joueur.sendMessage(ChatColor.DARK_AQUA + "Vous gagnez " + ChatColor.AQUA + finalAmount +  " Étoiles ! " + ChatColor.GREEN + "(" + reason + ")");
					}
				}
			});
		}
		return amountAfter;
	}

	@Override
	public long withdrawPlayerSynchronized(UUID player, int amount) {
		ShardedJedis jedis = api.getResource();
		long left = jedis.decrBy("stars:" + player, amount);
		jedis.close();

		return left;
	}

	@Override
	public boolean canPay(UUID player, int amount) {
		return getAmount(player) > amount;
	}

	@Override
	public int getAmount(UUID player) {
		String value = api.getDatabase().fastGet("stars:" + player);
		return (value == null) ? 0 : Integer.parseInt(value);
	}
}
