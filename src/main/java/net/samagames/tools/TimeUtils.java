package net.samagames.tools;

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
public class TimeUtils
{
    /**
     * Format message with a given second number
     *
     * @param baseMessage Raw message
     * @param time Time in seconds
     *
     * @return Formatted message
     */
    public static String formatTime(String baseMessage, int time)
    {
        int hours = time / 3600;
        int remainder = time - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        String timeStr = null;
        
        if (hours > 1)
        {
            if (secs == 0)
                if (mins == 30 || mins == 0)
                    timeStr = hours + "h" + mins;
        }
        else
        {
            if ((mins == 45 || mins == 30 || mins == 20 || mins == 10 || mins == 5 || mins == 3 || mins == 2 || mins == 1) && secs == 0)
                timeStr = mins + " minutes";
            
            if (mins == 1 && secs == 30)
                timeStr = mins + " minutes et " + secs + " secondes";
            
            if (mins == 0)
                if (secs == 30 || secs == 20 || secs == 10 || (secs <= 5 && secs > 0))
                    timeStr = secs + " secondes";
        }
        
        if (timeStr != null)
            return baseMessage.replace("${TIME}", timeStr);
        else
            return "";
    }
}
