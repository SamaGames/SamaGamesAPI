package net.samagames.tools;

import org.bukkit.Location;

public class Selection
{
    private Location firstPoint;
    private Location lastPoint;

    public Selection(Location firstPoint, Location lastPoint)
    {
        this.firstPoint = firstPoint;
        this.lastPoint = lastPoint;
    }

    public Selection()
    {
        this(null, null);
    }

    public void setFirstPoint(Location firstPoint)
    {
        this.firstPoint = firstPoint;
    }

    public void setLastPoint(Location lastPoint)
    {
        this.lastPoint = lastPoint;
    }

    public Location getMinimumPoint()
    {
        double x = Math.min(this.firstPoint.getX(), this.lastPoint.getBlockX());
        double y = Math.min(this.firstPoint.getY(), this.lastPoint.getBlockY());
        double z = Math.min(this.firstPoint.getZ(), this.lastPoint.getBlockZ());

        return new Location(this.firstPoint.getWorld(), x, y, z);
    }

    public Location getMaximumPoint()
    {
        double x = Math.max(this.firstPoint.getX(), this.lastPoint.getBlockX());
        double y = Math.max(this.firstPoint.getY(), this.lastPoint.getBlockY());
        double z = Math.max(this.firstPoint.getZ(), this.lastPoint.getBlockZ());

        return new Location(this.firstPoint.getWorld(), x, y, z);
    }
}
