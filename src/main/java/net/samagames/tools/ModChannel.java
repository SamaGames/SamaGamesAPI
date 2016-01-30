package net.samagames.tools;

import org.bukkit.ChatColor;

/**
 * Mod channels enum
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public enum ModChannel
{
    INFORMATION(ChatColor.GREEN, "Information"),
    DISCUSSION(ChatColor.DARK_AQUA, "Discussion"),
    SANCTION(ChatColor.RED, "Sanction"),
    REPORT(ChatColor.GOLD, "Signalement"),
    ;

    private final ChatColor color;
    private final String name;

    /**
     * Constructor
     *
     * @param color Prefix color
     * @param name Prefix name
     */
    ModChannel(ChatColor color, String name)
    {
        this.color = color;
        this.name = name;
    }

    /**
     * Get prefix color
     *
     * @return Color
     */
    public ChatColor getColor()
    {
        return this.color;
    }

    /**
     * Get prefix name
     *
     * @return Name
     */
    public String getName()
    {
        return this.name;
    }
}
