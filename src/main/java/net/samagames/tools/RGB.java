package net.samagames.tools;

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
public class RGB
{
    private final int r, g, b;

    /**
     * Constructor
     *
     * @param r Red color (max 255)
     * @param g Green color (max 255)
     * @param b Blue color (max 255)
     */
    public RGB(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Get red value
     *
     * @return Red color (between 0 and 255)
     */
    public int getRed()
    {
        return this.r;
    }

    /**
     * Get green value
     *
     * @return Green color (between 0 and 255)
     */
    public int getGreen()
    {
        return this.g;
    }

    /**
     * Get blue value
     *
     * @return Blue color (between 0 and 255)
     */
    public int getBlue()
    {
        return this.b;
    }
}
