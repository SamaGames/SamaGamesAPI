package net.samagames.api.achievements;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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
     */
    public IncrementationAchievement(int id, String displayName, AchievementCategory parentCategory, String[] description, int objective)
    {
        super(id, displayName, parentCategory, description);
        this.objective = objective;
    }

    /**
     * Increase the progress of a given player
     *
     * @param player Player
     * @param amount Amount
     */
    public void increment(UUID player, int amount)
    {
        AchievementProgress progress = this.progress.get(player);

        if (progress == null)
        {
            progress = new AchievementProgress(-1, 0, Timestamp.from(Instant.now()), null, true);
            this.progress.put(player, progress);
        }

        if (progress.getProgress() + amount > this.objective && progress.getUnlockTime() == null)
        {
            progress.unlock();
            progress.setProgress(this.objective);
            this.sendRewardMessage(player);
        }
        else if (progress.getUnlockTime() == null)
        {
            progress.setProgress(progress.getProgress() + amount);
        }
    }

    /**
     * Get the given player's progress of the achievement
     *
     * @param player Player
     *
     * @return Actual progress
     */
    public int getActualState(UUID player)
    {
        AchievementProgress progress = this.progress.get(player);
        return progress == null ? 0 : progress.getProgress();
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
