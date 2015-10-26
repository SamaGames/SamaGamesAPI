package net.samagames.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by {USER}
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */
public class Misc
{
    private static final Pattern URL = Pattern.compile("\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static final Pattern MOD = Pattern.compile("^.*@\\{(.*):(.*)\\}");

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

    public static Date parseTime(String str)
    {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
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

    public static Pattern getURLPattern()
    {
        return URL;
    }

    public static Pattern getModFunctionPattern()
    {
        return MOD;
    }
}
