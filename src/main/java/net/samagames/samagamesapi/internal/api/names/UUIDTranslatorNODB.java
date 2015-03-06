/**
 * Copyright © 2013 tuxed <write@imaginarycode.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 *
 * Updated by zyuiop to be used with Bukkit API
 */
package net.samagames.samagamesapi.internal.api.names;

import net.samagames.samagamesapi.api.names.UUIDTranslator;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Pattern;

public final class UUIDTranslatorNODB implements UUIDTranslator {
    private final Pattern UUID_PATTERN = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    private final Pattern MOJANGIAN_UUID_PATTERN = Pattern.compile("[a-fA-F0-9]{32}");
    private final Map<String, CachedUUIDEntry> nameToUuidMap = new ConcurrentHashMap<>(128, 0.5f, 4);
    private final Map<UUID, CachedUUIDEntry> uuidToNameMap = new ConcurrentHashMap<>(128, 0.5f, 4);

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

    private void persistInfo(String name, UUID uuid) {
        addToMaps(name, uuid);
    }

	@Override
	public UUID getUUID(String name, boolean allowMojangCheck) {
		if (!allowMojangCheck)
			return null;

		Map<String, UUID> uuidMap1;
		try {
			uuidMap1 = new UUIDFetcher(Collections.singletonList(name)).call();
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Unable to fetch UUID from Mojang for " + name, e);
			return null;
		}

		for (Map.Entry<String, UUID> entry : uuidMap1.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(name)) {
				persistInfo(entry.getKey(), entry.getValue());
				return entry.getValue();
			}
		}

		return null; // Nope, game over!
	}

	@Override
	public String getName(UUID uuid, boolean allowMojangCheck) {
		if (!allowMojangCheck)
			return null;

		// That didn't work. Let's ask Mojang. This call may fail, because Mojang is insane.
		String name;
		try {
			name = NameFetcher.nameHistoryFromUuid(uuid).get(0);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Unable to fetch name from Mojang for " + uuid, e);
			return null;
		}

		if (name != null) {
			persistInfo(name, uuid);
			return name;
		}

		return null;
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
