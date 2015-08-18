package net.samagames.api;

import net.samagames.api.achievements.IAchievementManager;
import net.samagames.api.friends.IFriendsManager;
import net.samagames.api.games.IGameManager;
import net.samagames.api.games.themachine.MachineListener;
import net.samagames.api.gui.IGuiManager;
import net.samagames.api.names.IUUIDTranslator;
import net.samagames.api.network.IProxyDataManager;
import net.samagames.api.parties.IPartiesManager;
import net.samagames.api.permissions.IPermissionsManager;
import net.samagames.api.player.IPlayerDataManager;
import net.samagames.api.pubsub.IPubSubAPI;
import net.samagames.api.resourcepacks.IResourcePacksManager;
import net.samagames.api.settings.ISettingsManager;
import net.samagames.api.shops.AbstractShopsManager;
import net.samagames.api.stats.AbstractStatsManager;
import net.samagames.tools.BarAPI.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.util.logging.Level;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public abstract class SamaGamesAPI {

	private static SamaGamesAPI instance;
    private JavaPlugin plugin;

	protected SamaGamesAPI(JavaPlugin pluginn) {
		instance = this;
        plugin = pluginn;

        Bukkit.getPluginManager().registerEvents(new MachineListener(), pluginn);
    }

	public static SamaGamesAPI get() {
		return instance;
	}

    public JavaPlugin getPlugin()
    {
        return this.plugin;
    }

    public void log(Level level, String text)
    {
        this.plugin.getLogger().log(level, text);
    }

	public abstract String getServerName();

    public abstract Jedis getResource();
    public abstract Jedis getBungeeResource();

	public abstract AbstractStatsManager getStatsManager(String game);
	public abstract AbstractShopsManager getShopsManager(String game);
	public abstract IGuiManager getGuiManager();
	public abstract ISettingsManager getSettingsManager();
	public abstract IPlayerDataManager getPlayerManager();
    public abstract IAchievementManager getAchievementManager();
	public abstract IUUIDTranslator getUUIDTranslator();
	public abstract IGameManager getGameManager();
    public abstract IPubSubAPI getPubSub();
	public abstract IResourcePacksManager getResourcePacksManager();
    public abstract IFriendsManager getFriendsManager();

	public abstract IProxyDataManager getProxyDataManager();
	public abstract IPartiesManager getPartiesManager();

	public abstract BarAPI getBarAPI();

	public abstract IPermissionsManager getPermissionsManager();

}
