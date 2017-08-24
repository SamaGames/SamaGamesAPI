package net.samagames.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

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
public class SimpleBlock
{
    private Material type;
    private byte data;

    /**
     * Constructor
     *
     * @param type Block's material
     * @param data Block's metadata
     */
    public SimpleBlock(Material type, byte data)
    {
        this.type = type;
        this.data = data;
    }

    /**
     * Constructor
     *
     * @param type Block's material
     * @param data Block's metadata
     */
    public SimpleBlock(Material type, int data)
    {
        this(type, (byte) data);
    }

    /**
     * Constructor
     *
     * @param type Block's material
     */
    public SimpleBlock(Material type)
    {
        this(type, (byte) 0);
    }

    /**
     * Constructor
     *
     * @param block Block
     */
    public SimpleBlock(Block block)
    {
        this(block.getType(), block.getData());
    }

    /**
     * Constructor
     *
     * @param location Block's location
     */
    public SimpleBlock(Location location)
    {
        this(location.getBlock());
    }

    /**
     * Get block's material
     *
     * @return Material
     */
    public Material getType()
    {
        return this.type;
    }

    /**
     * Get block's metadata
     *
     * @return Metadata
     */
    public byte getData()
    {
        return this.data;
    }
}
