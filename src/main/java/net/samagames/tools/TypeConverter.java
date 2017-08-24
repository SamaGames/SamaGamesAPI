package net.samagames.tools;

import java.sql.Timestamp;
import java.util.UUID;

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
