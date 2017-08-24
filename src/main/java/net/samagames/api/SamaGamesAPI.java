package net.samagames.api;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackAttachment;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import net.samagames.api.achievements.IAchievementManager;
import net.samagames.api.friends.IFriendsManager;
import net.samagames.api.games.IGameManager;
import net.samagames.api.gui.IGuiManager;
import net.samagames.api.names.IUUIDTranslator;
import net.samagames.api.network.IJoinManager;
import net.samagames.api.options.IServerOptions;
import net.samagames.api.parties.IPartiesManager;
import net.samagames.api.permissions.IPermissionsManager;
import net.samagames.api.player.IPlayerDataManager;
import net.samagames.api.pubsub.IPubSubAPI;
import net.samagames.api.resourcepacks.IResourcePacksManager;
import net.samagames.api.settings.ISettingsManager;
import net.samagames.api.shops.IShopsManager;
import net.samagames.api.stats.IStatsManager;
import net.samagames.tools.SkyFactory;
import net.samagames.tools.cameras.CameraManager;
import net.samagames.tools.npc.NPCManager;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.logging.Level;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public abstract class SamaGamesAPI
{
	private static SamaGamesAPI instance;
    private JavaPlugin plugin;

    /**
     * Constructor
     *
     * @param plugin Root plugin
     */
	public SamaGamesAPI(JavaPlugin plugin)
    {
		instance = this;
        this.plugin = plugin;
    }

    /**
     * Log a given message at the current level into
     * the console
     *
     * @param level Log level
     * @param text Log test
     */
    public void log(Level level, String text)
    {
        this.plugin.getLogger().log(level, text);
    }

    /**
     * Get the server name (for exemple UHCRun_435fvb345)
     *
     * @return Server's name
     */
	public abstract String getServerName();

    /**
     * Get the server options
     *
     * @return Server's options
     */
    public abstract IServerOptions getServerOptions();

    /**
     * Get an instance of the Redis database
     *
     * @return Jedis {@link Jedis} instance
     */
    public abstract Jedis getBungeeResource();

    /**
     * Get a new instance of the shop manager of
     * a given game code name
     *
     * @return New instance
     */
	public abstract IShopsManager getShopsManager();

    /**
     * Get instance of the stats manager
     *
     * @return Instance
     */
    public abstract IStatsManager getStatsManager();

    /**
     * Get the instance of the GUI manager
     *
     * @return Instance
     */
	public abstract IGuiManager getGuiManager();

    /**
     * Get the instance of the settings manager
     *
     * @return Instance
     */
	public abstract ISettingsManager getSettingsManager();

    /**
     * Get the instance of the player data manager
     *
     * @return Instance
     */
	public abstract IPlayerDataManager getPlayerManager();

    /**
     * Get the instance of the achievement manager
     *
     * @return Instance
     */
    public abstract IAchievementManager getAchievementManager();

    /**
     * Get the instance of the game manager
     *
     * @return Instance
     */
	public abstract IGameManager getGameManager();

    /**
     * Get the instance of the Redis PubSub API
     *
     * @return Instance
     */
    public abstract IPubSubAPI getPubSub();

    /**
     * Get the instance of the resource packs manager
     *
     * @return Instance
     */
	public abstract IResourcePacksManager getResourcePacksManager();

    /**
     * Get the instance of the friends manager
     *
     * @return Instance
     */
    public abstract IFriendsManager getFriendsManager();

    /**
     * Get the instance of the permissions manager manager
     *
     * @return Instance
     */
	public abstract IPermissionsManager getPermissionsManager();

    /**
     * Get the instance of the join manager
     *
     * @return Instance
     */
    public abstract IJoinManager getJoinManager();

    /**
     * Get the instance of the parties manager
     *
     * @return Instance
     */
	public abstract IPartiesManager getPartiesManager();

    /**
     * Get the instance of the NPC manager
     *
     * @return Instance
     */
    public abstract NPCManager getNPCManager();

    /**
     * Get the instance of the UUID translator
     *
     * @return Instance
     */
    public abstract IUUIDTranslator getUUIDTranslator();

    /**
     * Get the instance of the Sky Factory who
     * permits to change the "design" of a world
     *
     * @return Instance
     */
    public abstract SkyFactory getSkyFactory();

    /**
     * Get the instance of the Camera Manager who
     * permits to create fake camera for the
     * players
     *
     * @return Instance
     */
    public abstract CameraManager getCameraManager();

    public abstract Slack getSlackLogsPublisher();

    public void slackLog(Level level, SlackMessage message)
    {
        String color;

        if (level == Level.FINE)
            color = "#2FA44F";
        else if (level == Level.WARNING)
            color = "#DE9E31";
        else if (level == Level.SEVERE)
            color = "#D50200";
        else
            color = "#28D7E5";

        try
        {
            this.getSlackLogsPublisher().push(new SlackAttachment("").color(color).text(message));
        }
        catch (IOException ignored) {}
    }

    /**
     * Get the root plugin of the API
     *
     * @return Root plugin instance
     */
    public JavaPlugin getPlugin()
    {
        return this.plugin;
    }

    /**
     * Get the instance of the API
     *
     * @return Instance
     */
    public static SamaGamesAPI get()
    {
        return instance;
    }
}
