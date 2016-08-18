package net.samagames.api.achievements;

import net.md_5.bungee.api.ChatColor;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Achievement reward object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class AchievementReward
{
    private String coinsMessage;
    private String starsMessage;
    private int coins;
    private int stars;

    /**
     * Constructor
     */
    public AchievementReward()
    {
        this.coinsMessage = null;
        this.starsMessage = null;
    }

    /**
     * Give the reward of a given achievement to a given player
     *
     * @param player Player
     * @param achievement Achievement
     */
    public void give(Player player, Achievement achievement)
    {
        if (this.coinsMessage != null && this.coins > 0)
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditCoins(this.coins, this.coinsMessage, false);

        if (this.starsMessage != null && this.stars > 0)
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditStars(this.stars, this.starsMessage, false);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        player.sendMessage(ChatColor.DARK_AQUA + "♦ " + ChatColor.RESET + ChatColor.AQUA + "Objectif débloqué : " + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + achievement.getDisplayName() + ChatColor.RESET + ChatColor.DARK_AQUA + " ♦");
    }

    /**
     * Add coins into the reward
     *
     * @param coins Number of coins
     * @param coinsMessage Unlocking message
     *
     * @return Actual object
     */
    public AchievementReward setCoins(int coins, String coinsMessage)
    {
        this.coins = coins;
        this.coinsMessage = coinsMessage;

        return this;
    }

    /**
     * Add stars into the reward
     *
     * @param stars Number of stars
     * @param starsMessage Unlocking message
     *
     * @return Actual object
     */
    public AchievementReward setStars(int stars, String starsMessage)
    {
        this.stars = stars;
        this.starsMessage = starsMessage;

        return this;
    }
}
