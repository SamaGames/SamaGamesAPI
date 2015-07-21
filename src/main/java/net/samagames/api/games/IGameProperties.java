package net.samagames.api.games;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
     * @return The value
     */
    JsonPrimitive getOption(String key, JsonPrimitive defaultValue);

    /**
     * Get the game options
     *
     * @return The options
     */
    JsonObject getOptions();
}
