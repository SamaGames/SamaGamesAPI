package net.samagames.api.achievements;

import java.sql.Timestamp;
import java.time.Instant;

public class AchievementProgress
{
    private long progressId;
    private int progress;
    private Timestamp startTime;
    private Timestamp unlockTime;

    AchievementProgress(long progressId, int progress, Timestamp startTime, Timestamp unlockTime)
    {
        this.progressId = progressId;
        this.progress = progress;
        this.startTime = startTime;
        this.unlockTime = unlockTime;
    }

    /**
     * Get an increment achievement progress for this player
     *
     * @return progress
     */
    int getProgress()
    {
        return this.progress;
    }

    /**
     * Increment an achievement progress for this player
     *
     * @param amount Amount to increase
     */
    void setProgress(int amount)
    {
        this.progress = amount;
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
    }
}
