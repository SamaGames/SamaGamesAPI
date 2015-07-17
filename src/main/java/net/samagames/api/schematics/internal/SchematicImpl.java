package net.samagames.api.schematics.internal;

import net.minecraft.server.v1_8_R3.*;
import net.samagames.api.schematics.ISchematic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by Thog
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public final class SchematicImpl implements ISchematic
{

	private final int width, length, height;
	private final int[][][] blocks;
	private final byte[][][] metadata;
	private final Map<BlockPosition, TileEntity> tileEntities;
	private BlockPosition origin, offset;

	// Internal Class: DO NOT INSTANCE IT
	public SchematicImpl(final int width, final int height, final int length)
	{
		this.width = width;
		this.length = length;
		this.height = height;

		this.blocks = new int[width][height][length];
		this.metadata = new byte[width][height][length];
		this.tileEntities = new HashMap<>();
		this.origin = BlockPosition.ZERO;
		this.offset = BlockPosition.ZERO;
	}

	@Override
	public Material getMaterial(int x, int y, int z)
	{
		return isValid(x, y, z) ? Material.getMaterial(this.blocks[x][y][z]) : Material.AIR;
	}

	@Override
	public void setMaterial(int x, int y, int z, Material material, byte metadata)
	{
		if (isValid(x, y, z))
		{
			this.blocks[x][y][z] = material.getId();
			this.metadata[x][y][z] = metadata;
		}
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getLength()
	{
		return length;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public List<Material> getBlocks()
	{
		return null;
	}

	@Override
	public void paste(Location location)
	{
		this.fill(location);
		net.minecraft.server.v1_8_R3.World world = ((CraftWorld) location.getWorld()).getHandle();

		int worldX = location.getBlockX();
		int worldY = location.getBlockY();
		int worldZ = location.getBlockZ();

		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				for (int z = 0; z < length; z++)
				{
					Block block = (isValid(x, y, z) ? Block.getById(this.blocks[x][y][z]) : Blocks.AIR);
					BlockPosition worldPosition = new BlockPosition(worldX + x, worldY + y, worldZ + z).a(offset);
					BlockPosition internalPosition = new BlockPosition(x, y, z);
					world.setTypeAndData(worldPosition, block.fromLegacyData(this.metadata[x][y][z]), 3);
					if (this.tileEntities.containsKey(internalPosition))
						world.setTileEntity(worldPosition, this.tileEntities.get(internalPosition));
				}
			}
		}
	}

	@Override
	public void fill(Location location)
	{
		net.minecraft.server.v1_8_R3.World world = ((CraftWorld) location.getWorld()).getHandle();

		int worldX = location.getBlockX();
		int worldY = location.getBlockY();
		int worldZ = location.getBlockZ();
		IBlockData air = Blocks.AIR.getBlockData();
		for (int x = width; x > 0; x--)
		{
			for (int y = height; y > 0; y--)
			{
				for (int z = length; z > 0; z--)
				{
					BlockPosition worldPosition = new BlockPosition(worldX + x, worldY + y, worldZ + z).a(offset);
					world.setTypeAndData(worldPosition, air, 3);
				}
			}
		}
	}

	private boolean isValid(final int x, final int y, final int z)
	{
		return !(x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length);
	}

	public void setTileEntity(BlockPosition position, TileEntity tileEntity)
	{
		if (!tileEntities.containsKey(position))
		{
			tileEntities.put(position, tileEntity);
		}
	}


	public void setOffset(int x, int y, int z)
	{
		this.offset = new BlockPosition(x, y, z);
	}

	public void setOrigin(int x, int y, int z)
	{
		this.origin = new BlockPosition(x, y, z);
	}


	@Override
	public String toString()
	{
		return String.format("SchematicImpl[width: %d, height: %d, length: %d]", width, height, length);
	}
}
