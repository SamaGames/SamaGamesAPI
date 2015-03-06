package net.samagames.samagamesapi.utils;

public class RGB
{
    private final int r, g, b;
    
    public RGB(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public int getRed()
    {
        return this.r;
    }
    
    public int getGreen()
    {
        return this.g;
    }
    
    public int getBlue()
    {
        return this.b;
    }
}
