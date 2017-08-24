package net.samagames.tools;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.TileEntitySkull;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

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
public class BlockUtils
{
    /**
     * Set the given texture to a skull placed in the world
     *
     * @param texture Base64 texture
     */
    public static void setCustomSkull(Block block, String texture)
    {
        block.setType(Material.SKULL);

        Skull skullData = (Skull) block.getState();
        skullData.setSkullType(SkullType.PLAYER);

        World world = ((CraftWorld) block.getWorld()).getHandle();
        ((TileEntitySkull) world.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()))).setGameProfile(ItemUtils.getHeadCustomizedGameProfile(texture));

        block.getState().update(true);
    }
}
