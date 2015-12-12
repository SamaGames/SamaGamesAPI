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
    private final String id;
    private final String displayName;
    private final ItemStack icon;
    private final String[] description;

    /**
     * Constructor
     *
     * @param id Achievement category's ID
     * @param displayName Achievement category's display name in GUIs
     * @param icon Achievement category's icon {@link ItemStack} in GUIs
     * @param description Achievement category's description in GUIs
     */
    public AchievementCategory(String id, String displayName, ItemStack icon, String[] description)
    {
        this.id = id;
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
    }

    /**
     * Constructor
     *
     * @param id Achievement category's ID
     * @param displayName Achievement category's display name in GUIs
     * @param material Achievement category's icon {@link Material} in GUIs
     * @param description Achievement category's description in GUIs
     */
    public AchievementCategory(String id, String displayName, Material material, String[] description)
    {
        this(id, displayName, new ItemStack(material, 1), description);
    }

    /**
     * Get the achievement category's ID
     *
     * @return ID
     */
    public String getID()
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
}
