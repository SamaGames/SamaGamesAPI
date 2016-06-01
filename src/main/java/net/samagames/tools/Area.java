package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

/**
 * Area utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Area
{
    private final Location min;
    private final Location max;

    /**
     * Constructor
     *
     * @param first First point
     * @param second Second point
     */
    public Area(Location first, Location second)
    {
        this.min = new Location(null, 0, 0, 0);
        this.max = new Location(null, 0, 0, 0);

        if (first == null || second == null)
            return;

        if (first.getX() >= second.getX())
        {
            this.max.setX(first.getX());
            this.min.setX(second.getX());
        }
        else
        {
            this.max.setX(second.getX());
            this.min.setX(first.getX());
        }

        if (first.getY() >= second.getY())
        {
            this.max.setY(first.getY());
            this.min.setY(second.getY());
        }
        else
        {
            this.max.setY(second.getY());
            this.min.setY(first.getY());
        }

        if (first.getZ() >= second.getZ())
        {
            this.max.setZ(first.getZ());
            this.min.setZ(second.getZ());
        }
        else
        {
            this.max.setZ(second.getZ());
            this.min.setZ(first.getZ());
        }

        this.min.setWorld(first.getWorld());
        this.max.setWorld(first.getWorld());

    }

    /**
     * Get area size in X axis
     *
     * @return Size
     */
    public int getSizeX()
    {
        return this.max.getBlockX() - this.min.getBlockX();
    }

    /**
     * Get area size in Y axis
     *
     * @return Size
     */
    public int getSizeY()
    {
        return this.max.getBlockY() - this.min.getBlockY();
    }

    /**
     * Get area size in Z axis
     *
     * @return Size
     */
    public int getSizeZ()
    {
        return this.max.getBlockZ() - this.min.getBlockZ();
    }

    /**
     * Get lower point
     *
     * @return Location
     */
    public Location getMin()
    {
        return this.min;
    }

    /**
     * Get higher point
     *
     * @return Location
     */
    public Location getMax()
    {
        return this.max;
    }

    /**
     * Is given location in area
     *
     * @param loc Location
     *
     * @return {@code true} if in
     */
    public boolean isInArea(Location loc)
    {
        if (loc == null)
            return false;
        else if (loc.getX() > this.max.getX() || this.min.getX() > loc.getX())
            return false;
        else if (loc.getY() > this.max.getY() || this.min.getY() > loc.getY())
            return false;
        else if (loc.getZ() > this.max.getZ() || this.min.getZ() > loc.getZ())
            return false;

        return true;
    }

    /**
     * Is given location in range around the
     * area
     *
     * @param loc Location
     * @param range Range radius
     *
     * @return {@code true} if in
     */
    public boolean isInLimit(Location loc, int range)
    {
        if (loc == null)
            return false;
        else if (loc.getX() > this.max.getX() + range || this.min.getX() - range > loc.getX())
            return false;
        else if (loc.getY() > this.max.getY() + range || this.min.getY() - range > loc.getY())
            return false;
        else if (loc.getZ() > this.max.getZ() + range || this.min.getZ() - range > loc.getZ())
            return false;

        return true;
    }

    /**
     * Parse a structured string into a area
     * {@link Area}
     *
     * @param loc Structured string
     *
     * @return Area instance
     */
    public static Area str2area(String loc)
    {
        if (loc == null)
            return null;

        String[] location = loc.split(", ");

        if(location.length == 7)
        {
            World world = Bukkit.getWorld(location[0]);
            Location first = new Location(world, Double.parseDouble(location[1]), Double.parseDouble(location[2]), Double.parseDouble(location[3]));
            Location second = new Location(world, Double.parseDouble(location[4]), Double.parseDouble(location[5]), Double.parseDouble(location[6]));
            return new Area(first, second);
        }
        return null;
    }

    /**
     * Format a area into a structured string
     *
     * @param area Area
     *
     * @return Structured string
     */
    public static String loc2str(Area area)
    {
        return area.getMin().getWorld().getName() + ", " + area.getMin().getX() + ", " + area.getMin().getY() + ", " + area.getMin().getZ() + ", " + area.getMax().getX() + ", " + area.getMax().getY() + ", " + area.getMax().getZ();
    }
}
