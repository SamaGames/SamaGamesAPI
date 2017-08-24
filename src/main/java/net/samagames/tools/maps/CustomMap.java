package net.samagames.tools.maps;

import net.minecraft.server.v1_12_R1.PacketPlayOutMap;
import net.samagames.tools.Reflection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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
public class CustomMap
{
    private int xSize;
    private int ySize;
    private int id;
    byte[] bytes;

    CustomMap(int id, int xSize, int ySize)
    {
        this.xSize = xSize;
        this.ySize = ySize;
        this.id = id;
        this.bytes = new byte[xSize * ySize];
    }

    /**
     * Set a pixel color
     *
     * @param x x Coordinate
     * @param y y Coordinate
     * @param color Color as Byte, @<a href="http://minecraft.gamepedia.com/Map_item_format#1.8.1_Color_Table">http://minecraft.gamepedia.com/Map_item_format#1.8.1_Color_Table</a>
     */
    public void setPixel(int x, int y, byte color)
    {
        if (x < 0 || x >= this.xSize)
            throw new IllegalArgumentException("x out of range (" + x + ")");
        if (y < 0 || y >= this.ySize)
            throw new IllegalArgumentException("y out of range (" + y + ")");

        this.bytes[y * this.xSize + x] = color;
    }

    /**
     * Sends the map to a player, needed to draw it
     *
     * @param player Player
     */
    public void sendToPlayer(Player player)
    {
        Reflection.sendPacket(player, new PacketPlayOutMap(this.id, (byte)0, false, new ArrayList<>(), this.bytes, 0, 0, this.xSize, this.ySize));
    }

    /**
     * Get this map as ItemStack
     *
     * @return ItemStack
     */
    public ItemStack toItemStack()
    {
        return new ItemStack(Material.MAP, 1, (short)this.id);
    }
}
