package net.samagames.api.achievements;

import net.samagames.tools.chat.fanciful.FancyMessage;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

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
    private static final FireworkEffect FIREWORK_EFFECT;

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
            return;

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
            return;

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

        Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(2);
        fireworkMeta.addEffect(FIREWORK_EFFECT);
        firework.setFireworkMeta(fireworkMeta);

        String[] array = new String[this.description.length + 2];
        array[0] = ChatColor.AQUA + this.displayName;
        array[1] = "";

        for (int i = 0; i < this.description.length; i++)
            array[i + 2] = ChatColor.GRAY + this.description[i];

        String finalDisplayName = "";

        for (char letter : this.getDisplayName().toCharArray())
            finalDisplayName += ChatColor.AQUA + "" + letter;

        FancyMessage message = new FancyMessage(ChatColor.DARK_AQUA + "\u25A0 ")
                .then(ChatColor.AQUA + player.getName())
                .then(ChatColor.WHITE + " a débloqué l'objectif : ")
                .then(finalDisplayName)
                    .tooltip(array)
                .then(ChatColor.WHITE + " !")
                .then(ChatColor.DARK_AQUA + " \u25A0");

        Bukkit.getOnlinePlayers().forEach(message::send);
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

    static
    {
        FIREWORK_EFFECT = FireworkEffect.builder().with(FireworkEffect.Type.STAR).withColor(Color.BLUE).withColor(Color.AQUA).withColor(Color.WHITE).build();
    }
}
