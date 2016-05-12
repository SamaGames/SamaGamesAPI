package net.samagames.api.settings;

import java.util.UUID;

/**
 * Settings manager class
 * Created by Silvanosky
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface ISettingsManager
{
    /**
     * Get the settings object of a given player
     *
     * @param player Player's UUID
	 *
	 * @return Setting's object values
     */
    IPlayerSettings getSettings(UUID player);

}
