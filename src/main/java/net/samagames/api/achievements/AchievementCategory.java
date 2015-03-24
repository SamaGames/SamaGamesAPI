package net.samagames.api.achievements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AchievementCategory
{
    private final String id;
    private final String displayName;
    private final ItemStack icon;
    private final String[] description;

    public AchievementCategory(String id, String displayName, ItemStack icon, String[] description)
    {
        this.id = id;
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
    }

    public AchievementCategory(String id, String displayName, Material material, String[] description)
    {
        this(id, displayName, new ItemStack(material, 1), description);
    }

    public String getID()
    {
        return this.id;
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
