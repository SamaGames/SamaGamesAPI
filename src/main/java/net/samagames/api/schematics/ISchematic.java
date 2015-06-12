package net.samagames.api.schematics;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by Thog
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ISchematic
{

	/**
	 * Gets the material at a given location within the schematic. Requesting a block outside of those bounds
	 * returns a air block.
	 *
	 * @param x the x coord in world space.
	 * @param y the y coord in world space.
	 * @param z the z coord in world space.
	 * @return the block at the requested location.
	 */
	Material getMaterial(final int x, final int y, final int z);

	void setMaterial(int x, int y, int z, Material material, byte metadata);

	/**
	 * The width of the schematic
	 *
	 * @return the schematic width
	 */
	int getWidth();

	/**
	 * The length of the schematic
	 *
	 * @return the schematic length
	 */
	int getLength();

	/**
	 * The height of the schematic
	 *
	 * @return the schematic height
	 */
	int getHeight();

	/**
	 * Returns a list of all blocks in the schematic.
	 *
	 * @return all blocks.
	 */
	List<Material> getBlocks();

	/**
	 * Paste the schematic at the given location
	 *
	 * @param location paste location
	 */
	void paste(Location location);


	/**
	 * Apply air block at the given location to the end schematic location
	 *
	 * @param location fill location
	 */
	void fill(Location location);


}
