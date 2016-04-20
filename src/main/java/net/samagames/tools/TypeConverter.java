package net.samagames.tools;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by Silvanosky on 30/03/2016.
 */
public class TypeConverter {

    public static <T> T convert(Class<T> a, String value)
    {
        if (a == int.class)
        {
            return (T) Integer.valueOf(value);
        }else if (a == Timestamp.class)
        {
            return (T) Timestamp.valueOf(value);
        }else if (a == long.class)
        {
            return (T) Long.valueOf(value);
        }else if (a == java.util.UUID.class)
        {
            return (T) UUID.fromString(value);
        }else if (a == double.class)
        {
            return (T) Double.valueOf(value);
        }else if (a == boolean.class)
        {
            return (T) Boolean.valueOf(value);
        }

        return (T)value;
    }


}
