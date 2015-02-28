package net.samagames.internal.api.settings;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.settings.SettingsManager;
import redis.clients.jedis.ShardedJedis;

import java.util.Map;
import java.util.UUID;

public class SettingsManagerDB implements SettingsManager {

	protected SamaGamesAPI api;

	public SettingsManagerDB(SamaGamesAPI api) {
		this.api = api;
	}

    public Map<String, String> getSettings(UUID player) {
        ShardedJedis j = api.getDatabase().getResource();
        Map<String, String> r = j.hgetAll("settings:" + player);
		j.close();
        return r;
    }

    public String getSetting(UUID player, String setting) {
		ShardedJedis j = api.getDatabase().getResource();
        String r = j.hget("settings:" + player, setting);
		j.close();
        return r;
    }

    public void setSetting(UUID player, String setting, String value) {
		ShardedJedis j = api.getDatabase().getResource();
        j.hset("settings:" + player, setting, value);
		j.close();
    }
}
