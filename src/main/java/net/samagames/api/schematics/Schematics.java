package net.samagames.api.schematics;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.samagames.api.schematics.internal.SchematicImpl;
import org.bukkit.Material;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by Thog
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class Schematics
{

	/**
	 * Permite to load a schematic
	 *
	 * @param file The schematic
	 * @return a ISchematic object
	 * @throws IOException when the file doesn't exist
	 */
	public static ISchematic load(File file) throws IOException
	{
		if (!file.exists())
			throw new IOException(file.getAbsolutePath() + ": File not found");

		FileInputStream in = new FileInputStream(file);
		NBTTagCompound tagCompound = NBTCompressedStreamTools.a(in);

		// Read schematic size
		short width = tagCompound.getShort("Width");
		short length = tagCompound.getShort("Length");
		short height = tagCompound.getShort("Height");
		SchematicImpl schematic = new SchematicImpl(width, height, length);
		byte[] blocks = tagCompound.getByteArray("Blocks");
		byte[] metadata = tagCompound.getByteArray("Data");

		short originX = tagCompound.getShort("WEOriginX");
		short originY = tagCompound.getShort("WEOriginY");
		short originZ = tagCompound.getShort("WEOriginZ");
		schematic.setOrigin(originX, originY, originZ);

		short offsetX = tagCompound.getShort("WEOffsetX");
		short offsetY = tagCompound.getShort("WEOffsetY");
		short offsetZ = tagCompound.getShort("WEOffsetZ");
		schematic.setOffset(offsetX, offsetY, offsetZ);

		// Detect Blocks and metadata
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				for (int z = 0; z < length; z++)
				{
					int index = x + (y * length + z) * width;
					Material material = Material.getMaterial(blocks[index] & 0xFF);
					byte meta = (byte) (metadata[index] & 0xFF);
					schematic.setMaterial(x, y, z, material, meta);
				}
			}
		}

		NBTTagList tileEntities = tagCompound.getList("TileEntities", 10);

		for (int i = 0; i < tileEntities.size(); i++)
		{
			TileEntity tileEntity = TileEntity.c(tileEntities.get(i));
			if (tileEntity != null)
			{
				schematic.setTileEntity(tileEntity.getPosition(), tileEntity);
			}
		}


		in.close();
		return schematic;
	}
}
