package net.samagames.tools.maps;

import org.bukkit.map.MapPalette;

import java.awt.*;

/**
 * Created by Rigner on 04/09/2016 for project SamaGamesAPI.
 * All rights reserved.
 */
public class MapUtils
{
    private static int IDS = Short.MAX_VALUE;

    public CustomMap createMap()
    {
        return new CustomMap(MapUtils.IDS--, 128, 128);
    }

    @SuppressWarnings("deprecation")
    public CustomMap fromImage(Image image)
    {
        CustomMap customMap = new CustomMap(MapUtils.IDS--, image.getWidth(null), image.getHeight(null));
        customMap.bytes = MapPalette.imageToBytes(image);
        return customMap;
    }
}
