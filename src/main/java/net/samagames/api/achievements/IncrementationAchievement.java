package net.samagames.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.AbstractPlayerData;
import org.bukkit.entity.Player;

/**
 * Incrementation achievement object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class IncrementationAchievement extends Achievement
{
    private final int objective;

    /**
     * Constructor
     *
     * @param id Achievement ID
     * @param displayName Achievement's display name in GUIs
     * @param parentCategory Achievement's parent category ID
     * @param description Achievement's description in GUIs
     * @param objective Achievement's goal to reach
     * @param reward Achievement's reward
     */
    public IncrementationAchievement(String id, String displayName, String parentCategory, String[] description, int objective, AchievementReward reward)
    {
        super(id, displayName, parentCategory, description, reward);
        this.objective = objective;
    }

    /**
     * Increase the progress of a given player
     *
     * @param player Player
     */
    public void increment(Player player)
    {
        /*AbstractPlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
        Integer before = playerData.getInt("achievement:" + this.id);

        if(before == null)
            before = 0;

        int now = before + 1;

        if(now > this.objective)
            return;

        if(now < this.objective)
        {
            playerData.setInt("achievement:" + this.id, now);
            return;
        }

        this.unlock(player);*/
    }

    /**
     * Get the given player's progress of the achievement
     *
     * @param player Player
     *
     * @return Actual progress
     */
    public int getActualState(Player player)
    {
       /* AbstractPlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
        Integer actual = playerData.getInt("achievement:" + this.id);

        return (actual == null ? 0 : actual);*/
        return 0;
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
