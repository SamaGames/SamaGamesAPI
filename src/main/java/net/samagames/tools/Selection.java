package net.samagames.tools;

import org.bukkit.Location;

/**
 * Selection utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Selection
{
    private Location firstPoint;
    private Location lastPoint;

    /**
     * Constructor
     *
     * @param firstPoint First point of the selection
     * @param lastPoint Last point of the selection
     */
    public Selection(Location firstPoint, Location lastPoint)
    {
        this.firstPoint = firstPoint;
        this.lastPoint = lastPoint;
    }

    /**
     * Set the first point of the selection
     *
     * @param firstPoint First point
     */
    public void setFirstPoint(Location firstPoint)
    {
        this.firstPoint = firstPoint;
    }

    /**
     * Set the last point of the selection
     *
     * @param lastPoint Last point
     */
    public void setLastPoint(Location lastPoint)
    {
        this.lastPoint = lastPoint;
    }

    /**
     * Get the lower point
     *
     * @return A point
     */
    public Location getMinimumPoint()
    {
        double x = Math.min(this.firstPoint.getX(), this.lastPoint.getBlockX());
        double y = Math.min(this.firstPoint.getY(), this.lastPoint.getBlockY());
        double z = Math.min(this.firstPoint.getZ(), this.lastPoint.getBlockZ());

        return new Location(this.firstPoint.getWorld(), x, y, z);
    }

    /**
     * Get the higher point
     *
     * @return A point
     */
    public Location getMaximumPoint()
    {
        double x = Math.max(this.firstPoint.getX(), this.lastPoint.getBlockX());
        double y = Math.max(this.firstPoint.getY(), this.lastPoint.getBlockY());
        double z = Math.max(this.firstPoint.getZ(), this.lastPoint.getBlockZ());

        return new Location(this.firstPoint.getWorld(), x, y, z);
    }
}
