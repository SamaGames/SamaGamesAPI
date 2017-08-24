package net.samagames.tools.maps;

import org.bukkit.map.MapPalette;

import java.awt.*;

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
public class MapUtils
{
    private static int IDS = Short.MAX_VALUE;

    private MapUtils()
    {
    }

    /**
     * Create a new map
     *
     * @return Map
     */
    public static CustomMap createMap()
    {
        return new CustomMap(MapUtils.IDS--, 128, 128);
    }

    /**
     * Create a new map from an image
     *
     * @param image Image
     * @return Map
     */
    @SuppressWarnings("deprecation")
    public static CustomMap fromImage(Image image)
    {
        CustomMap customMap = new CustomMap(MapUtils.IDS--, image.getWidth(null), image.getHeight(null));
        customMap.bytes = MapPalette.imageToBytes(image);
        return customMap;
    }
}
