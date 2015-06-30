package net.samagames.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.PlayerData;
import org.bukkit.entity.Player;

public class IncrementationAchievement extends Achievement
{
    private final int objective;

    public IncrementationAchievement(String id, String displayName, String parentCategory, String[] description, int objective, AchievementReward reward)
    {
        super(id, displayName, parentCategory, description, reward);
        this.objective = objective;
    }

    public void increment(Player player)
    {
        PlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());

        Integer before = playerData.getInt("achievement:" + this.id);

        if(before == null)
            before = 0;

        int now = before + 1;

        if(now > this.objective)
            return;

        if(now < this.objective)
        {
            playerData.setInt("achievement:" + this.id, now);
            return;
        }

        this.unlock(player);
    }

    public int getActualState(Player player)
    {
        PlayerData playerData = SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId());
        Integer actual = playerData.getInt("achievement:" + this.id);

        return (actual == null ? 0 : actual);
    }

    public int getObjective()
    {
        return this.objective;
    }
}
