package net.samagames.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Achievement
{
    private UUID uuid;
    private String displayName;
    private UUID parentCategory;
    private String[] description;
    private AchievementReward reward;

    public Achievement(UUID uuid, String displayName, UUID parentCategory, String[] description, AchievementReward reward)
    {
        this.uuid = uuid;
        this.displayName = displayName;
        this.parentCategory = parentCategory;
        this.description = description;
        this.reward = reward;
    }

    public void unlock(Player player)
    {
        PlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
        playerData.set("achievement:" + this.uuid.toString(), "unlocked");

        this.reward.give(player, this);
    }

    public int increment(Player player)
    {
        PlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());

        int before = playerData.getInt("achievement:" + this.uuid.toString());
        int now = before + 1;

        playerData.setInt("achievement:" + this.uuid.toString(), now);

        return now;
    }

    public Achievement setUUID(UUID uuid)
    {
        this.uuid = uuid;
        return this;
    }

    public Achievement setDisplayName(String displayName)
    {
        this.displayName = displayName;
        return this;
    }

    public Achievement setParentCategory(UUID parentCategory)
    {
        this.parentCategory = parentCategory;
        return this;
    }

    public Achievement setDesciption(String[] description)
    {
        this.description = description;
        return this;
    }

    public Achievement setReward(AchievementReward reward)
    {
        this.reward = reward;
        return this;
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public UUID getParentCategoryID()
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
