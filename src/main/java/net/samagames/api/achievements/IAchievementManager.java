package net.samagames.api.achievements;

import java.util.List;
import java.util.UUID;

/**
 * Achievement manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IAchievementManager
{
    /**
     * Increase the progress of a given achievement to a given player
     *
     * @param player Player
     * @param achievement Achievement
     * @param amount Amount
     */
    void incrementAchievement(UUID player, IncrementationAchievement achievement, int amount);

    /**
     * Increase the progress of a given achievement to a given player
     *
     * @param player Player
     * @param achievement Achievement
     * @param amount Amount
     */
    void incrementAchievement(UUID player, int achievement, int amount);

    /**
     * Increase achievements progress, usefull for linked achievements
     *
     * @param player Player
     * @param achievements Achievement id array
     * @param amount Amount
     */
    void incrementAchievements(UUID player, int[] achievements, int amount);

    /**
     * Get the achievement with the given ID
     *
     * @param id ID
     *
     * @return Achievement
     */
    Achievement getAchievementByID(int id);

    /**
     * Get the achievement category with the given ID
     *
     * @param id ID
     *
     * @return Achievement category
     */
    AchievementCategory getAchievementCategoryByID(int id);

    /**
     * Get all the achievements of the database
     *
     * @return Achievements
     */
    List<Achievement> getAchievements();

    /**
     * Get all the achievement categories of the database
     *
     * @return Achievement categories
     */
    List<AchievementCategory> getAchievementsCategories();

    /**
     * Return if the given player has unlocked the given achievement
     *
     * @param player Player
     * @param achievement Achievement
     *
     * @return {@code true} if unlocked
     */
    boolean isUnlocked(UUID player, Achievement achievement);

    /**
     * Return if the given player has unlocked the given achievement ID
     *
     * @param player Player
     * @param id Achievement's ID
     *
     * @return {@code true} if unlocked
     */
    boolean isUnlocked(UUID player, int id);
}
