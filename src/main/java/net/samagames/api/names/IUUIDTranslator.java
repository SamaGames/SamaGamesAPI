package net.samagames.api.names;

import java.util.UUID;

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
