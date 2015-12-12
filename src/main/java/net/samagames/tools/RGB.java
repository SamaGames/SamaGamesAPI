package net.samagames.tools;

/**
 * RGB utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
