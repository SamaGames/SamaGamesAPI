package net.samagames.api.achievements;

/**
 * Achievement object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Achievement
{
    private final int id;
    private final String displayName;
    private final AchievementCategory parentCategory;
    private final String[] description;
    private final AchievementReward reward;
    private final int objective;

    /**
     * Constructor
     *
     * @param id Achievement ID
     * @param displayName Achievement's display name in GUIs
     * @param parentCategory Achievement's parent category ID
     * @param description Achievement's description in GUIs
     * @param reward Achievement's reward
     */
    Achievement(int id, String displayName, AchievementCategory parentCategory, String[] description, AchievementReward reward, int objective)
    {
        this.id = id;
        this.displayName = displayName;
        this.parentCategory = parentCategory;
        this.description = description;
        this.reward = reward;
        this.objective = objective;
    }

    /**
     * Get the achievement's ID
     *
     * @return ID
     */
    public int getID()
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
    public AchievementCategory getParentCategoryID()
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

    /**
     * Get the goal to reach to unlock the achievement
     *
     * @return Goal
     */
    public int getObjective()
    {
        return this.objective;
    }
}
