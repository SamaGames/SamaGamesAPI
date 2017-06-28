package net.samagames.tools;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.TileEntitySkull;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

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
