package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;

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
public class LocationUtils
{
    /**
     * Parse a structured string into a location
     * {@link Location}
     *
     * @param loc Structured string
     *
     * @return Location instance
     */
    public static Location str2loc(String loc)
    {
        if (loc == null)
            return null;

        String[] location = loc.split(", ");

        if(location.length == 6)
            return new Location(Bukkit.getServer().getWorld(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]), Double.parseDouble(location[3]), Float.parseFloat(location[4]), Float.parseFloat(location[5]));
        else
            return new Location(Bukkit.getServer().getWorld(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]), Double.parseDouble(location[3]));
    }

    /**
     * Format a location into a structured string
     *
     * @param loc Location
     *
     * @return Structured string
     */
    public static String loc2str(Location loc)
    {
        return loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch();
    }
}
