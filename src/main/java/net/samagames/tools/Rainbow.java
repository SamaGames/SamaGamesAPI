package net.samagames.tools;

import org.bukkit.ChatColor;

import java.util.ArrayList;

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
public class Rainbow
{
    /**
     * Get a ordered list of color to make
     * a beautiful rainbow
     *
     * @return A list of color
     */
    public static ArrayList<ChatColor> getRainbow()
    {
        ArrayList<ChatColor> rainbowContent = new ArrayList<>();

        rainbowContent.add(ChatColor.DARK_RED);
        rainbowContent.add(ChatColor.RED);
        rainbowContent.add(ChatColor.GOLD);
        rainbowContent.add(ChatColor.YELLOW);
        rainbowContent.add(ChatColor.GREEN);
        rainbowContent.add(ChatColor.DARK_AQUA);
        rainbowContent.add(ChatColor.DARK_BLUE);
        rainbowContent.add(ChatColor.DARK_AQUA);
        rainbowContent.add(ChatColor.GREEN);
        rainbowContent.add(ChatColor.YELLOW);
        rainbowContent.add(ChatColor.GOLD);
        rainbowContent.add(ChatColor.RED);
        rainbowContent.add(ChatColor.DARK_RED);

        return rainbowContent;
    }
}
