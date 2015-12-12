package net.samagames.tools;

import java.util.Date;

/**
 * Promo object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Promo
{
    public Date end;
    public int multiply = 1;
    public String message = null;

    /**
     * Constructor
     *
     * @param promoData Raw data
     */
    public Promo(String promoData)
    {
        String[] split = promoData.split(":");
        this.multiply = Integer.parseInt(split[0]);

        this.end = new Date();
        this.end.setTime(Long.parseLong(split[1]));

        if (split.length > 2)
            this.message = split[2];
    }

    @Override
    public String toString()
    {
        return this.multiply + ":" + this.end.getTime() + ((this.message != null) ? ":" + this.message : "");
    }
}
