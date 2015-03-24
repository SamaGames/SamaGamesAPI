package net.samagames.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerData;
import org.bukkit.entity.Player;

public class Achievement
{
    private final String id;
    private final String displayName;
    private final String parentCategory;
    private final String[] description;
    private final AchievementReward reward;

    public Achievement(String id, String displayName, String parentCategory, String[] description, AchievementReward reward)
    {
        this.id = id;
        this.displayName = displayName;
        this.parentCategory = parentCategory;
        this.description = description;
        this.reward = reward;
    }

    public void unlock(Player player)
    {
        PlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
        playerData.set("achievement:" + this.id, "unlocked");

        this.reward.give(player, this);
    }

    public int increment(Player player)
    {
        PlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());

        int before = playerData.getInt("achievement:" + this.id);
        int now = before + 1;

        playerData.setInt("achievement:" + this.id, now);

        return now;
    }

    public String getID()
    {
        return this.id;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String getParentCategoryID()
    {
        return this.parentCategory;
    }

    public String[] getDescription()
    {
        return this.description;
    }

    public AchievementReward getReward()
    {
        return this.reward;
    }
}
