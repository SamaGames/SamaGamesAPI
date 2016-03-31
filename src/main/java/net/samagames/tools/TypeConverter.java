package net.samagames.tools;

import java.sql.Timestamp;

/**
 * Created by Silvanosky on 30/03/2016.
 */
public class TypeConverter {

    public static <T> T convert(Class<T> a, String value)
    {
        if (a == int.class)
        {
            return (T)Integer.valueOf(value);
        }else if (a == Timestamp.class)
        {
            return (T)Timestamp.valueOf(value);
        }else if (a == long.class)
        {
            return (T)Long.valueOf(value);
        }

        return (T)value;
    }


}
