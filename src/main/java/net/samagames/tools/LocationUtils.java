package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Location utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
