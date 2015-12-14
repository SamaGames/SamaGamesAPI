package net.samagames.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Simple block utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
