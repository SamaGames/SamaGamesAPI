package net.samagames.api.names;

import java.util.UUID;

/**
 * UUID translator class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IUUIDTranslator
{
	/**
	 * Get the UUID of a given username
     *
	 * @param name Username
	 * @param allowMojangCheck Allow Mojang asking
     *
	 * @return UUID
	 */
    UUID getUUID(String name, boolean allowMojangCheck);

    /**
     * Get the UUID of a given username
     *
     * @param name Username
     *
     * @return UUID
     */
    default UUID getUUID(String name)
    {
		return this.getUUID(name, false);
	}

    /**
     * Get the username of a given UUID
     *
     * @param uuid UUID
     * @param allowMojangCheck Allow Mojang asking
     *
     * @return Username
     */
    String getName(UUID uuid, boolean allowMojangCheck);

    /**
     * Get the username of a given UUID
     *
     * @param uuid UUID
     *
     * @return Username
     */
    default String getName(UUID uuid)
    {
		return this.getName(uuid, false);
	}
}
