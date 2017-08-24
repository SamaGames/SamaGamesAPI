package net.samagames.api.achievements;

import java.sql.Timestamp;
import java.time.Instant;

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
public class AchievementProgress
{
    private long progressId;
    private int progress;
    private Timestamp startTime;
    private Timestamp unlockTime;
    private boolean changed;

    AchievementProgress(long progressId, int progress, Timestamp startTime, Timestamp unlockTime, boolean changed)
    {
        this.progressId = progressId;
        this.progress = progress;
        this.startTime = startTime;
        this.unlockTime = unlockTime;
        this.changed = changed;
    }

    /**
     * Get an increment achievement progress for this player
     *
     * @return progress
     */
    public int getProgress()
    {
        return this.progress;
    }

    /**
     * Increment an achievement progress for this player
     *
     * @param amount Amount to increase
     */
    public void setProgress(int amount)
    {
        this.progress = amount;
        this.changed = true;
    }

    /**
     * Get start time for this achievement progress
     *
     * @return Start time
     */
    public Timestamp getStartTime()
    {
        return this.startTime;
    }

    /**
     * Get when this player unlocked this achievement
     *
     * @return Unlock time
     */
    public Timestamp getUnlockTime()
    {
        return this.unlockTime;
    }

    /**
     * Get this progress id
     *
     * @return Id
     */
    public long getProgressId()
    {
        return this.progressId;
    }

    /**
     * Internal
     * Unlock achievement
     */
    void unlock()
    {
        this.unlockTime = Timestamp.from(Instant.now());
        this.changed = true;
    }

    /**
     * Internal
     *
     * @return Check if this achievements progress as changed and must be updated
     */
    public boolean isChanged()
    {
        return this.changed;
    }
}
