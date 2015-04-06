/**
 * Copyright © 2013 tuxed <write@imaginarycode.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 *
 * Updated by zyuiop to be used with Bukkit API
 */
package net.samagames.core.api.names;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.names.UUIDTranslator;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Pattern;

public final class UUIDTranslatorDB implements UUIDTranslator {
    private final Pattern UUID_PATTERN = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    private final Pattern MOJANGIAN_UUID_PATTERN = Pattern.compile("[a-fA-F0-9]{32}");
    private final Map<String, CachedUUIDEntry> nameToUuidMap = new ConcurrentHashMap<>(128, 0.5f, 4);
    private final Map<UUID, CachedUUIDEntry> uuidToNameMap = new ConcurrentHashMap<>(128, 0.5f, 4);
	private final SamaGamesAPI api;
	private final APIPlugin plugin;

	public UUIDTranslatorDB(APIPlugin plugin, SamaGamesAPI api) {
		this.api = api;
		this.plugin = plugin;
	}

	private void addToMaps(String name, UUID uuid) {
        // This is why I like LocalDate...

        // Cache the entry for three days.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);

        // Create the entry and populate the local maps
        CachedUUIDEntry entry = new CachedUUIDEntry(name, uuid, calendar);
        nameToUuidMap.put(name.toLowerCase(), entry);
        uuidToNameMap.put(uuid, entry);
    }

    private void persistInfo(String name, UUID uuid, Jedis jedis) {
        addToMaps(name, uuid);
        jedis.hset("uuid-cache", name.toLowerCase(), new Gson().toJson(uuidToNameMap.get(uuid)));
        jedis.hset("uuid-cache", uuid.toString(), new Gson().toJson(uuidToNameMap.get(uuid)));
    }

	@Override
	public UUID getUUID(String name, boolean allowMojangCheck) {
		// If the player is online, give them their UUID.
		// Remember, local data > remote data.
		if (Bukkit.getPlayer(name) != null)
			return Bukkit.getPlayer(name).getUniqueId();

		// Check if it exists in the map
		CachedUUIDEntry cachedUUIDEntry = nameToUuidMap.get(name.toLowerCase());
		if (cachedUUIDEntry != null) {
			if (!cachedUUIDEntry.expired())
				return cachedUUIDEntry.getUuid();
			else
				nameToUuidMap.remove(name);
		}

		// Check if we can exit early
		if (UUID_PATTERN.matcher(name).find()) {
			return UUID.fromString(name);
		}

		if (MOJANGIAN_UUID_PATTERN.matcher(name).find()) {
			// Reconstruct the UUID
			return UUIDFetcher.getUUID(name);
		}

		// Let's try Redis.
		try (Jedis jedis = api.getBungeeResource()) {
			String stored = jedis.hget("uuid-cache", name.toLowerCase());
			if (stored != null) {
				// Found an entry value. Deserialize it.
				CachedUUIDEntry entry = new Gson().fromJson(stored, CachedUUIDEntry.class);

				// Check for expiry:
				if (entry.expired()) {
					jedis.hdel("uuid-cache", name.toLowerCase());
				} else {
					nameToUuidMap.put(name.toLowerCase(), entry);
					uuidToNameMap.put(entry.getUuid(), entry);
					return entry.getUuid();
				}
			}

			// That didn't work. Let's ask Mojang.
			if (!allowMojangCheck)
				return null;

			Map<String, UUID> uuidMap1;
			try {
				uuidMap1 = new UUIDFetcher(Collections.singletonList(name)).call();
			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Unable to fetch UUID from Mojang for " + name, e);
				return null;
			}
			for (Map.Entry<String, UUID> entry : uuidMap1.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(name)) {
					persistInfo(entry.getKey(), entry.getValue(), jedis);
					return entry.getValue();
				}
			}
		} catch (JedisException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to fetch UUID for " + name, e);
		}

		return null; // Nope, game over!
	}

	@Override
	public String getName(UUID uuid, boolean allowMojangCheck) {
		if (Bukkit.getPlayer(uuid) != null)
			return Bukkit.getPlayer(uuid).getName();

		// Check if it exists in the map
		CachedUUIDEntry cachedUUIDEntry = uuidToNameMap.get(uuid);
		if (cachedUUIDEntry != null) {
			if (!cachedUUIDEntry.expired())
				return cachedUUIDEntry.getName();
			else
				uuidToNameMap.remove(uuid);
		}

		// Okay, it wasn't locally cached. Let's try Redis.
		try (Jedis jedis = api.getBungeeResource()) {
			String stored = jedis.hget("uuid-cache", uuid.toString());
			if (stored != null) {
				// Found an entry value. Deserialize it.
				CachedUUIDEntry entry = new Gson().fromJson(stored, CachedUUIDEntry.class);

				// Check for expiry:
				if (entry.expired()) {
					jedis.hdel("uuid-cache", uuid.toString());
				} else {
					nameToUuidMap.put(entry.getName().toLowerCase(), entry);
					uuidToNameMap.put(uuid, entry);
					return entry.getName();
				}
			}

			if (!allowMojangCheck)
				return null;

			// That didn't work. Let's ask Mojang. This call may fail, because Mojang is insane.
			String name;
			try {
				name = NameFetcher.nameHistoryFromUuid(uuid).get(0);
			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Unable to fetch name from Mojang for " + uuid, e);
				return null;
			}

			if (name != null) {
				persistInfo(name, uuid, jedis);
				return name;
			}

			return null;
		} catch (JedisException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to fetch name for " + uuid, e);
			return null;
		}
	}

	private static class CachedUUIDEntry {
        private final String name;
        private final UUID uuid;
        private final Calendar expiry;

        public boolean expired() {
            return Calendar.getInstance().after(expiry);
        }

        public CachedUUIDEntry(String name, UUID uuid, Calendar expiry) {
            this.name = name;
            this.uuid = uuid;
            this.expiry = expiry;
        }

        public String getName() {
            return name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public Calendar getExpiry() {
            return expiry;
        }
    }
}
