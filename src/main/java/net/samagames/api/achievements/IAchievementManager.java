package net.samagames.api.achievements;

import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Achievement manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IAchievementManager
{
    /**
     * Reload achievement list locally
     */
    void reloadList();

    /**
     * Increase the progress of a given achievement to a given player
     *
     * @param player Player
     * @param achievement Achievement
     */
    void incrementAchievement(Player player, IncrementationAchievement achievement);

    /**
     * Get the achievement with the given ID
     *
     * @param id ID
     *
     * @return Achievement
     */
    Achievement getAchievementByID(String id);

    /**
     * Get the achievement category with the given ID
     *
     * @param id ID
     *
     * @return Achievement category
     */
    AchievementCategory getAchievementCategoryByID(String id);

    /**
     * Get all the achievements of the database
     *
     * @return Achievements
     */
    ArrayList<Achievement> getAchievements();

    /**
     * Get all the achievement categories of the database
     *
     * @return Achievement categories
     */
    ArrayList<AchievementCategory> getAchievementsCategories();

    /**
     * Return if the given player has unlocked the given achievement
     *
     * @param player Player
     * @param achievement Achievement
     *
     * @return {@code true} if unlocked
     */
    boolean isUnlocked(Player player, Achievement achievement);

    /**
     * Return if the given player has unlocked the given achievement ID
     *
     * @param player Player
     * @param id Achievement's ID
     *
     * @return {@code true} if unlocked
     */
    boolean isUnlocked(Player player, String id);
}
