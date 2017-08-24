package net.samagames.tools;

import org.bukkit.Color;

import java.util.Random;

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
public class ColorUtils
{
    private static Random random = new Random();

    /**
     * Pick a random color
     *
     * @return Color
     */
    public static Color randomColor()
    {
        return getColor((random.nextInt(17) + 1));
    }

    /**
     * Get a color with a number (to work with
     * randoms)
     *
     * @param i Number of the color
     *
     * @return Color
     */
    public static Color getColor(int i)
    {
        Color c = null;

        if (i == 1)
            c = Color.AQUA;
        else if (i == 2)
            c = Color.BLACK;
        else if (i == 3)
            c = Color.BLUE;
        else if (i == 4)
            c = Color.FUCHSIA;
        else if (i == 5)
            c = Color.GRAY;
        else if (i == 6)
            c = Color.GREEN;
        else if (i == 7)
            c = Color.LIME;
        else if (i == 8)
            c = Color.MAROON;
        else if (i == 9)
            c = Color.NAVY;
        else if (i == 10)
            c = Color.OLIVE;
        else if (i == 11)
            c = Color.ORANGE;
        else if (i == 12)
            c = Color.PURPLE;
        else if (i == 13)
            c = Color.RED;
        else if (i == 14)
            c = Color.SILVER;
        else if (i == 15)
            c = Color.TEAL;
        else if (i == 16)
            c = Color.WHITE;
        else if (i == 17)
            c = Color.YELLOW;

        return c;
    }
}
