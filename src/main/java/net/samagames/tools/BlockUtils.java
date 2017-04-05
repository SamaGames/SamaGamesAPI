package net.samagames.tools;

import com.mojang.authlib.GameProfile;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlockUtils
{
    private static Class<?> craftWorldClass;
    private static Class<?> blockPositionClass;
    private static Class<?> tileEntitySkullTile;

    private static Method getTileEntityMethod;
    private static Method setGameProfileMethod;

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

        try
        {
            Object craftWorld = Reflection.getHandle(craftWorldClass.cast(block.getWorld()));
            Object blockPosition = blockPositionClass.getDeclaredConstructor(int.class, int.class, int.class).newInstance(block.getX(), block.getY(), block.getZ());
            Object skullTile = getTileEntityMethod.invoke(craftWorld, blockPosition);

            setGameProfileMethod.invoke(skullTile, ItemUtils.getHeadCustomizedGameProfile(texture));
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        block.getState().update(true);
    }

    static
    {
        try
        {
            craftWorldClass = Reflection.getNMSClass("CraftWorld");
            blockPositionClass = Reflection.getNMSClass("BlockPosition");
            tileEntitySkullTile = Reflection.getNMSClass("TileEntitySkull");

            getTileEntityMethod = Reflection.getMethod(craftWorldClass, "getTileEntity", blockPositionClass);
            setGameProfileMethod = Reflection.getMethod(tileEntitySkullTile, "setGameProfile", GameProfile.class);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }
}
