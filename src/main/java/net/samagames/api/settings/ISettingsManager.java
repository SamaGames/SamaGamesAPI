package net.samagames.api.settings;

import java.util.Map;
import java.util.UUID;

/**
 * Settings manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface ISettingsManager
{
    /**
     * Set a setting to a given player
     *
     * @param player Player's UUID
     * @param setting Setting's name
     * @param value	Setting's value
     */
    void setSetting(UUID player, String setting, String value);

    /**
     * Set a setting to a given player
     *
     * @param player Player's UUID
     * @param setting Setting's name
     * @param value	Setting's value
     * @param callback Callback fired after operation
     */
    void setSetting(UUID player, String setting, String value, Runnable callback);

	/**
	 * Get the given setting's value of a given player
     *
	 * @param player Player's UUID
	 * @param setting Setting
     *
	 * @return Setting's value or {@code null} if not found
	 */
    String getSetting(UUID player, String setting);

    /**
     * Get the settings of given player
     *
     * @param player Player's UUID
     *
     * @return A map ordered by key and values
     */
    Map<String, String> getSettings(UUID player);

	/**
	 * Determinate if a given setting is enabled for
     * a given player
     *
	 * @param player Player's UUID
	 * @param setting Setting key
	 * @param def Default value of not found
     *
	 * @return Setting value or {@code null} if not found
	 */
    default boolean isEnabled(UUID player, String setting, boolean def)
    {
		String val = getSetting(player, setting);
        return (val == null ? def : Boolean.valueOf(val));
	}
}
