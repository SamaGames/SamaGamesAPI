package net.samagames.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerData;
import org.bukkit.entity.Player;

public class Achievement
{
    protected final String id;
    protected final String displayName;
    protected final String parentCategory;
    protected final String[] description;
    protected final AchievementReward reward;

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
