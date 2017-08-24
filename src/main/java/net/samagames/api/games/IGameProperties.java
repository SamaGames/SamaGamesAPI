package net.samagames.api.games;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
public interface IGameProperties
{
    /**
     * Reload the properties from the file (root/game.json)
     */
    void reload();

    /**
     * Get the map name
     *
     * @return The name
     */
    String getMapName();

    /**
     * Get the minimum slots of the game
     *
     * @return The number
     */
    int getMinSlots();

    /**
     * Get the maximum slots of the game
     *
     * @return The number
     */
    int getMaxSlots();

    /**
     * Get a game option
     *
     * @param key Option key
     * @param defaultValue Default value
     *
     * @return The value
     */
    JsonElement getGameOption(String key, JsonElement defaultValue);

    /**
     * Get the game options
     *
     * @return The options
     */
    JsonObject getGameOptions();

    /**
     * Get a game option
     *
     * @param key Option key
     * @param defaultValue Default value
     *
     * @return The value
     */
    JsonElement getMapProperty(String key, JsonElement defaultValue);
    
    /**
     * Get the map properties
     * 
     * @return The properties
     */
    JsonObject getMapProperties();
}