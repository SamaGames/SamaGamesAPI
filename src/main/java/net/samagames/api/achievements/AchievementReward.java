package net.samagames.api.achievements;

import net.md_5.bungee.api.ChatColor;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AchievementReward
{
    private String coinsMessage;
    private String starsMessage;
    private int coins;
    private int stars;

    public AchievementReward()
    {
        this.coinsMessage = null;
        this.starsMessage = null;
    }

    public void give(Player player, Achievement achievement)
    {
        if(this.coinsMessage != null && this.coins > 0)
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditCoins(this.coins, this.coinsMessage, false);

        if(this.starsMessage != null && this.stars > 0)
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditStars(this.stars, this.starsMessage, false);

        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + ChatColor.MAGIC + "$$$ " + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD + "Objectif débloqué : " + ChatColor.RESET + ChatColor.GOLD + achievement.getDisplayName() + ChatColor.RESET + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.MAGIC + " $$$");
    }

    public AchievementReward setCoins(int coins, String coinsMessage)
    {
        this.coins = coins;
        this.coinsMessage = coinsMessage;

        return this;
    }

    public AchievementReward setStars(int stars, String starsMessage)
    {
        this.stars = stars;
        this.starsMessage = starsMessage;

        return this;
    }
}
