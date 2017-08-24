package net.samagames.api.network;

import java.util.List;
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
public interface IJoinManager
{
    /**
     * Register a JoinHandler which is going to be called by the manager
     *
     * @param handler The handler
     * @param priority The handler priority (0 = Lowest, please do not use priorities under 10)
     */
    void registerHandler(IJoinHandler handler, int priority);

    /**
     * Count connected players
     *
     * @return Number of players
     */
    int countExpectedPlayers();

    /**
     * Get connected players as a list of UUID
     *
     * @return List of UUID
     */
    List<UUID> getExpectedPlayers();

    /**
     * Get connected moderator as a list of UUID
     *
     * @return List of UUID
     */
    List<UUID> getModeratorsExpected();
}
