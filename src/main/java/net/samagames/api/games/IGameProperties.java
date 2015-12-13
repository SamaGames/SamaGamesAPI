package net.samagames.api.games;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Game properties manager
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
    JsonElement getOption(String key, JsonElement defaultValue);

    /**
     * Get the game options
     *
     * @return The options
     */
    JsonObject getOptions();

    /**
     * Get a game option
     *
     * @param key Option key
     * @param defaultValue Default value
     *
     * @return The value
     */
    JsonElement getConfig(String key, JsonElement defaultValue);
    
    /**
     * Get the map properties
     * 
     * @return The properties
     */
    JsonObject getConfigs();
}