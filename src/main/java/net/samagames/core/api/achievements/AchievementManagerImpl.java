package net.samagames.core.api.achievements;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.achievements.Achievement;
import net.samagames.api.achievements.AchievementCategory;
import net.samagames.api.achievements.AchievementManager;
import net.samagames.api.achievements.AchievementReward;
import net.samagames.core.APIPlugin;
import net.samagames.tools.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.logging.Level;

public class AchievementManagerImpl implements AchievementManager
{
    private final SamaGamesAPI api;

    private ArrayList<Achievement> achievements;
    private ArrayList<AchievementCategory> achievementCategories;

    public AchievementManagerImpl(SamaGamesAPI api)
    {
        this.api = api;

        this.achievements = new ArrayList<>();
        this.achievementCategories = new ArrayList<>();

        this.reloadList();

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(APIPlugin.getInstance(), this::reloadList, 20L * 60 * 5, 20L * 60 * 5);
    }

    @Override
    public void reloadList()
    {
        APIPlugin.log(Level.INFO, "Reloading achievement list...");

        this.achievements.clear();
        this.achievementCategories.clear();

        if(!this.api.getResource().exists("achievements:list"))
            this.api.getResource().set("achievements:list", "{\"categories\":[],\"achievements\":[]}");

        String json = this.api.getResource().get("achievements:list");
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);

        JsonArray jsonCategories = jsonObject.get("categories").getAsJsonArray();

        for(int i = 0; i < jsonCategories.size(); i++)
        {
            JsonObject jsonCategory = jsonCategories.get(i).getAsJsonObject();
            String categoryID = jsonCategory.get("uuid").getAsString();
            String categoryDisplayName = jsonCategory.get("display-name").getAsString();
            ItemStack categoryIcon = ItemUtils.strToStack(jsonCategory.get("icon").getAsString());
            String[] categoryDescription = jsonCategory.get("description").getAsString().split("::");

            this.achievementCategories.add(new AchievementCategory(categoryID, categoryDisplayName, categoryIcon, categoryDescription));
            APIPlugin.log(Level.INFO, "Registered achievement category '" + categoryID + "'");
        }

        JsonArray jsonAchievements = jsonObject.get("achievements").getAsJsonArray();

        for(int i = 0; i < jsonAchievements.size(); i++)
        {
            JsonObject jsonAchievement = jsonAchievements.get(i).getAsJsonObject();
            String achievementID = jsonAchievement.get("id").getAsString();
            String achievementDisplayName = jsonAchievement.get("display-name").getAsString();
            String achievementParentID = jsonAchievement.get("parent-id").getAsString();
            String[] achievementDescription = jsonAchievement.get("description").getAsString().split("::");

            JsonObject jsonReward = jsonAchievement.get("reward").getAsJsonObject();
            AchievementReward reward = new AchievementReward();
            reward.setCoins(jsonReward.get("coins").getAsInt(), jsonReward.get("coins-message").getAsString());
            reward.setStars(jsonReward.get("stars").getAsInt(), jsonReward.get("stars-message").getAsString());

            this.achievements.add(new Achievement(achievementID, achievementDisplayName, achievementParentID, achievementDescription, reward));
            APIPlugin.log(Level.INFO, "Registered achievement '" + achievementID + "'");
        }
    }

    @Override
    public int incrementAchievement(Player player, Achievement achievement)
    {
        return achievement.increment(player);
    }

    @Override
    public Achievement getAchievementByName(String name)
    {
        for(Achievement achievement : this.achievements)
            if(achievement.getID().equals(name))
                return achievement;

        return null;
    }

    @Override
    public AchievementCategory getAchievementCategoryByName(String name)
    {
        for(AchievementCategory category : this.achievementCategories)
            if(category.getID().equals(name))
                return category;

        return null;
    }

    @Override
    public ArrayList<Achievement> getAchievements()
    {
        return this.achievements;
    }

    @Override
    public ArrayList<AchievementCategory> getAchievementsCategories()
    {
        return this.achievementCategories;
    }

    public boolean isUnlocked(Player player, Achievement achievement)
    {
        return (this.api.getPlayerManager().getPlayerData(player.getUniqueId()).get("achievements:" + achievement.getID()) != null && this.api.getPlayerManager().getPlayerData(player.getUniqueId()).get("achievements:" + achievement.getID()).equals("unlocked"));
    }

    @Override
    public boolean isUnlocked(Player player, String achievement)
    {
        if(this.getAchievementByName(achievement) == null)
            return false;

        return this.isUnlocked(player, this.getAchievementByName(achievement));
    }
}
