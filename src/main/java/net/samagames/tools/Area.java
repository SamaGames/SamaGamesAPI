package net.samagames.tools;

import org.bukkit.Location;

import java.util.List;

public class Area
{
    private final Location min;
    private final Location max;

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

    public Area(List<String> src)
    {
        this(LocationUtils.str2loc(src.get(0)), LocationUtils.str2loc(src.get(1)));
    }

    public Location getMin() {
        return this.min;
    }

    public Location getMax() {
        return this.max;
    }

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

    public boolean isInLimit(Location loc, int range)
    {
        if (loc == null)
            return false;
        else if (loc.getX() > max.getX() - range || min.getX() + range > loc.getX())
            return true;
        else if (loc.getZ() > max.getZ() - range || min.getZ() + range > loc.getZ())
            return true;

        return false;
    }

    public int getSizeX() { return max.getBlockX() - min.getBlockX(); }
    public int getSizeY() { return max.getBlockY() - min.getBlockY(); }
    public int getSizeZ() { return max.getBlockZ() - min.getBlockZ(); }
}
