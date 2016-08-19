package net.samagames.api.achievements;

import org.bukkit.entity.Player;

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
     * Reload achievement list locally
     */
    void reloadList();

    /**
     * Increase the progress of a given achievement to a given player
     *
     * @param achievement Achievement
     * @param uuid Player's uuid
     */
    void incrementAchievement(Achievement achievement, UUID uuid, int amount);

    /**
     * Increase the progress of a given achievement to a given player
     *
     * @param id Achievement's id
     * @param uuid Player's uuid
     */
    void incrementAchievement(int id, UUID uuid, int amount);

    /**
     * Unlock achievement for a player
     *
     * @param achievement Achievement
     * @param uuid Player's uuid
     */
    void unlockAchievement(Achievement achievement, UUID uuid);

    /**
     * Unlock achievement for a player
     *
     * @param id Achievement's id
     * @param uuid Player's uuid
     */
    void unlockAchievement(int id, UUID uuid);

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
     * @param uuid Player's uuid
     * @param achievement Achievement
     *
     * @return {@code true} if unlocked
     */
    boolean isUnlocked(UUID uuid, Achievement achievement);

    /**
     * Return if the given player has unlocked the given achievement ID
     *
     * @param uuid Player's uuid
     * @param id Achievement's ID
     *
     * @return {@code true} if unlocked
     */
    boolean isUnlocked(UUID uuid, int id);
}
