package net.samagames.tools;

import org.bukkit.entity.Player;

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
public class DirectionUtils
{
    public enum Directions { NORTH, SOUTH, EAST, WEST }

    /**
     * Get given player's location with his location
     *
     * @param player Player
     *
     * @return Enumeration entry {@link Directions}
     */
    public static Directions getPlayerDirection(Player player)
    {
        double rotation = (player.getLocation().getYaw() - 90) % 360;

        if (rotation < 0)
            rotation += 360.0;

        if (0 <= rotation && rotation < 22.5)
            return Directions.NORTH;
        else if (67.5 <= rotation && rotation < 112.5)
            return Directions.EAST;
        else if (157.5 <= rotation && rotation < 202.5)
            return Directions.SOUTH;
        else if (247.5 <= rotation && rotation < 292.5)
            return Directions.WEST;
        else if (337.5 <= rotation && rotation < 360.0)
            return Directions.NORTH;
        else
            return null;
    }
}
