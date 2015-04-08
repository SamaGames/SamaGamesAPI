package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class TimedReconnectHandler implements ReconnectHandler {

	private final HashMap<UUID, Integer> playerDisconnectTime = new HashMap<>();
	private final HashMap<UUID, BukkitTask> playerReconnectedTimers = new HashMap<>();
	private final ArrayList<UUID> playersDisconnected = new ArrayList<>();
	private int maxReconnectTime;
	private IReconnectGame game;

	public TimedReconnectHandler(int maxReconnectTime) {
		this.maxReconnectTime = maxReconnectTime;
	}

	@Override
	public void disconnect(Player player) {
		this.playersDisconnected.add(player.getUniqueId());
		Jedis jedis = SamaGamesAPI.get().getBungeeResource();
		jedis.set("rejoin:" + player.getUniqueId().toString(), SamaGamesAPI.get().getServerName());
		jedis.expire("rejoin:" + player.getUniqueId().toString(), this.maxReconnectTime * 60);

		this.playerReconnectedTimers.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimerAsynchronously(APIPlugin.getInstance(), new Runnable() {
			int before = 0;
			int now = 0;
			boolean bool = false;

			@Override
			public void run() {
				if (! this.bool) {
					if (playerDisconnectTime.containsKey(player.getUniqueId()))
						this.before = playerDisconnectTime.get(player.getUniqueId());

					this.bool = true;
				}

				if (this.before == maxReconnectTime * 2 || this.now == maxReconnectTime) {
					onTimeout(player);
					return;
				}

				this.before++;
				this.now++;
				playerDisconnectTime.put(player.getUniqueId(), before);
			}
		}, 20L, 20L));
	}

	@Override
	public void reconnect(Player player) {
		if(this.playerReconnectedTimers.containsKey(player.getUniqueId())) {
			Integer value = playerDisconnectTime.remove(player.getUniqueId());
			BukkitTask task = playerReconnectedTimers.remove(player.getUniqueId());
			if (task != null)
				task.cancel();

			if (value == null || value <= maxReconnectTime) {
				game.playerReconnect(player);
			}
		}
	}

	@Override
	public boolean canReconnect(UUID player) {
		if(this.playerReconnectedTimers.containsKey(player)) {
			Integer value = playerDisconnectTime.get(player);
			return (value != null && value < maxReconnectTime);
		}
		return false;
	}

	void onTimeout(Player player) {
		playerDisconnectTime.remove(player.getUniqueId());
		BukkitTask task = playerReconnectedTimers.remove(player.getUniqueId());
		if (task != null)
			task.cancel();

		game.playerReconnectTimeOut(player);
	}

	@Override
	public void asign(IReconnectGame game) {
		this.game = game;
	}
}
