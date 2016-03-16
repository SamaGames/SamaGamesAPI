package net.samagames.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.AbstractPlayerData;
import org.bukkit.entity.Player;

/**
 * Achievement object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Achievement
{
    protected final String id;
    protected final String displayName;
    protected final String parentCategory;
    protected final String[] description;
    protected final AchievementReward reward;

    /**
     * Constructor
     *
     * @param id Achievement ID
     * @param displayName Achievement's display name in GUIs
     * @param parentCategory Achievement's parent category ID
     * @param description Achievement's description in GUIs
     * @param reward Achievement's reward
     */
    public Achievement(String id, String displayName, String parentCategory, String[] description, AchievementReward reward)
    {
        this.id = id;
        this.displayName = displayName;
        this.parentCategory = parentCategory;
        this.description = description;
        this.reward = reward;
    }

    /**
     * Unlock this achievement for a given player
     *
     * @param player Player
     */
    public void unlock(Player player)
    {
        AbstractPlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
        //playerData.set("achievement:" + this.id, "unlocked");
        //Todo persistance api

        this.reward.give(player, this);
    }

    /**
     * Get the achievement's ID
     *
     * @return ID
     */
    public String getID()
    {
        return this.id;
    }

    /**
     * Get the achievement's display name in GUIs
     *
     * @return Display name
     */
    public String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * Get the achievement's parent category ID
     *
     * @return Parent category ID
     */
    public String getParentCategoryID()
    {
        return this.parentCategory;
    }

    /**
     * Get the achievement's description in GUIs
     *
     * @return Description
     */
    public String[] getDescription()
    {
        return this.description;
    }

    /**
     * Get the achievement's reward
     *
     * @return Reward object
     */
    public AchievementReward getReward()
    {
        return this.reward;
    }
}
