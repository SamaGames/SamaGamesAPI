package net.samagames.tools;

import org.bukkit.ChatColor;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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
