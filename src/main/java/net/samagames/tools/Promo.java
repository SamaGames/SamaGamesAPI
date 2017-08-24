package net.samagames.tools;

import java.util.Date;

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
