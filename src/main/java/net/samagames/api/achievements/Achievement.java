package net.samagames.api.achievements;

import net.samagames.tools.chat.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * Achievement object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Achievement
{
    protected final int id;
    protected final String displayName;
    protected final AchievementCategory parentCategory;
    protected final String[] description;
    protected Map<UUID, AchievementProgress> progress;

    /**
     * Constructor
     *
     * @param id Achievement ID
     * @param displayName Achievement's display name in GUIs
     * @param parentCategory Achievement's parent category ID
     * @param description Achievement's description in GUIs
     */
    public Achievement(int id, String displayName, AchievementCategory parentCategory, String[] description)
    {
        this.id = id;
        this.displayName = displayName;
        this.parentCategory = parentCategory;
        this.description = new String[description.length];
        for (int i = 0; i < description.length; i++)
            this.description[i] = ChatColor.translateAlternateColorCodes('&', description[i]);
        this.progress = new HashMap<>();
    }

    /**
     * Unlock this achievement for a given player
     *
     * @param player Player
     */
    public void unlock(UUID player)
    {
        if (this instanceof IncrementationAchievement)
            throw new IllegalStateException("Try to unlock incrementation achievement");
        AchievementProgress progress = this.progress.get(player);
        if (progress != null && progress.getUnlockTime() != null)
            return ;
        if (progress == null)
        {
            progress = new AchievementProgress(-1, 0, Timestamp.from(Instant.now()), null);
            this.progress.put(player, progress);
        }
        progress.unlock();
        progress.setProgress(1);
        this.sendRewardMessage(player);
    }

    /**
     * Send reward message to player
     *
     * @param uuid Player
     */
    protected void sendRewardMessage(UUID uuid)
    {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null)
            return ;
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        List<String> list = new ArrayList<>();
        list.add(ChatColor.DARK_PURPLE + this.getDisplayName());
        list.add("");
        Collections.addAll(list, this.getDescription());
        new FancyMessage(ChatColor.DARK_AQUA + "♦ " + ChatColor.AQUA + "Objectif débloqué : " + ChatColor.GOLD + ChatColor.BOLD).then(this.getDisplayName()).tooltip(list).then(ChatColor.DARK_AQUA + " ♦").send(player);
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
     * Get if this achievement is unlocked for a given player
     *
     * @param player Player
     * @return {@code true} if unlocked
     */
    public boolean isUnlocked(UUID player)
    {
        AchievementProgress progress = this.progress.get(player);
        return progress != null && progress.getUnlockTime() != null;
    }

    /**
     * Internal function, should only be used by API
     *
     * @param uuid Player
     * @param progressId Progress id
     * @param progress Progress
     * @param startTime Start time
     * @param unlockTime Unlock time
     */
    public void addProgress(UUID uuid, long progressId, int progress, Timestamp startTime, Timestamp unlockTime)
    {
        this.progress.put(uuid, new AchievementProgress(progressId, progress, startTime, unlockTime));
    }

    /**
     * Internal function, should only be used by API
     *
     * @param uuid Player
     */
    public void removeProgress(UUID uuid)
    {
        this.progress.remove(uuid);
    }

    /**
     * Internal function, should only be used by API
     *
     * @param uuid Player
     * @return Progress
     */
    public AchievementProgress getProgress(UUID uuid)
    {
        return this.progress.get(uuid);
    }
}
