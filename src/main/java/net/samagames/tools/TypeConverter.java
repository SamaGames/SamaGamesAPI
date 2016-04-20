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
            System.out.print("Convertion int: " + value);
            return (T) (Integer)Integer.parseInt(value);
        }else if (a == Timestamp.class)
        {
            return (T)Timestamp.valueOf(value);
        }else if (a == long.class)
        {
            return (T)Long.valueOf(value);
        }else if (a == UUID.class)
        {
            return (T) UUID.fromString(value);
        }else if (a == double.class)
        {
            return (T) (Double)Double.parseDouble(value);
        }

        return (T)value;
    }


}
