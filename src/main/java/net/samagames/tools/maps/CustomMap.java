package net.samagames.tools.maps;

import net.minecraft.server.v1_9_R2.PacketPlayOutMap;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Rigner on 04/09/2016 for project SamaGamesAPI.
 * All rights reserved.
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

    public void setPixel(int x, int y, byte color)
    {
        if (x < 0 || x >= this.xSize)
            throw new IllegalArgumentException("x out of range (" + x + ")");
        if (y < 0 || y >= this.ySize)
            throw new IllegalArgumentException("y out of range (" + y + ")");

        this.bytes[y * this.xSize + x] = color;
    }

    public void sendToPlayer(Player player)
    {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutMap(this.id, (byte)0, false, new ArrayList<>(), this.bytes, 0, 0, this.xSize, this.ySize));
    }

    public ItemStack toItemStack()
    {
        return new ItemStack(Material.MAP, 1, (short)this.id);
    }
}
