package net.samagames.api.achievements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class AchievementCategory
{
    private UUID uuid;
    private String displayName;
    private ItemStack icon;
    private String[] description;

    public AchievementCategory(UUID uuid, String displayName, ItemStack icon, String[] description)
    {
        this.uuid = uuid;
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
    }

    public AchievementCategory(UUID uuid, String displayName, Material material, String[] description)
    {
        this(uuid, displayName, new ItemStack(material, 1), description);
    }

    public AchievementCategory setUUID(UUID uuid)
    {
        this.uuid = uuid;
        return this;
    }

    public AchievementCategory setDisplayName(String displayName)
    {
        this.displayName = displayName;
        return this;
    }

    public AchievementCategory setIcon(ItemStack icon)
    {
        this.icon = icon;
        return this;
    }

    public AchievementCategory setDescription(String[] description)
    {
        this.description = description;
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

    public ItemStack getIcon()
    {
        return this.icon;
    }

    public String[] getDescription()
    {
        return this.description;
    }
}
