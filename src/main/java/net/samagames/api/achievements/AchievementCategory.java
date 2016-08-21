package net.samagames.api.achievements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Achievement category object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class AchievementCategory
{
    private final int id;
    private final String displayName;
    private final ItemStack icon;
    private final String[] description;
    private final AchievementCategory parent;

    /**
     * Constructor
     *
     * @param id Achievement category's ID
     * @param displayName Achievement category's display name in GUIs
     * @param icon Achievement category's icon {@link ItemStack} in GUIs
     * @param description Achievement category's description in GUIs
     */
    public AchievementCategory(int id, String displayName, ItemStack icon, String[] description, AchievementCategory parent)
    {
        this.id = id;
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
        this.parent = parent;
    }

    /**
     * Constructor
     *
     * @param id Achievement category's ID
     * @param displayName Achievement category's display name in GUIs
     * @param material Achievement category's icon {@link Material} in GUIs
     * @param description Achievement category's description in GUIs
     */
    public AchievementCategory(int id, String displayName, Material material, String[] description, AchievementCategory parent)
    {
        this(id, displayName, new ItemStack(material, 1), description, parent);
    }

    /**
     * Get the achievement category's ID
     *
     * @return ID
     */
    public int getID()
    {
        return this.id;
    }

    /**
     * Get the achievement category's display name in GUIs
     *
     * @return Display name
     */
    public String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * Get the achievement category's icon in GUIs
     *
     * @return Icon
     */
    public ItemStack getIcon()
    {
        return this.icon;
    }

    /**
     * Get the achievement category's description in GUIs
     *
     * @return Description
     */
    public String[] getDescription()
    {
        return this.description;
    }

    /**
     * Get the achievement category's parent category
     *
     * @return Parent category
     */
    public AchievementCategory getParent()
    {
        return this.parent;
    }
}
