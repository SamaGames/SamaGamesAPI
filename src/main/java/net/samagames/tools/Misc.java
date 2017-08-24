package net.samagames.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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
public class Misc
{
    private static final Pattern URL = Pattern.compile("\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    /**
     * Format a millisecond based long into a
     * structured phrase
     *
     * @param time Milliseconds
     *
     * @return Structured phrase
     */
    public static String formatTime(long time)
    {
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        String ret = "";
        if (days > 0)
            ret += days + " jours ";

        if (hours > 0)
            ret += hours + " heures ";

        if (minutes > 0)
            ret += minutes + " minutes ";

        if (seconds > 0)
            ret += seconds + " secondes";

        if (ret.isEmpty() && minutes == 0)
            ret += "moins d'une minute";

        return ret;
    }

    /**
     * Parse a raw phrase into a date {@link Date}
     *
     * @param str Raw phrase
     *
     * @return Date object
     */
    public static Date parseTime(String str)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        for (String t : str.split("\\+"))
        {
            String[] end = t.split(":");
            int type;

            if (end[1].equalsIgnoreCase("d"))
                type = Calendar.DAY_OF_YEAR;
            else if (end[1].equalsIgnoreCase("h"))
                type = Calendar.HOUR;
            else if (end[1].equalsIgnoreCase("m"))
                type = Calendar.MINUTE;
            else
                type = Calendar.SECOND;

            cal.add(type, Integer.parseInt(end[0]));
        }
        return cal.getTime();
    }

    /**
     * Get the URL regex pattern
     *
     * @return Instance
     */
    public static Pattern getURLPattern()
    {
        return URL;
    }
}
