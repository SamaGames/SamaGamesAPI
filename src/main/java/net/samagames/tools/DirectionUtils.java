package net.samagames.tools;

import org.bukkit.entity.Player;

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
