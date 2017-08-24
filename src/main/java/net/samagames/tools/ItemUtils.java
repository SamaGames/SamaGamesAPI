package net.samagames.tools;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

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
public class ItemUtils
{
    /**
     * Open the given written book
     *
     * @param book Written book
     * @param player Player
     */
    public static void openWrittenBook(ItemStack book, Player player)
    {
        if (book.getType() != Material.WRITTEN_BOOK)
            return;

        ItemStack previous = player.getInventory().getItemInHand();
        player.getInventory().setItemInHand(book);

        ByteBuf buffer = Unpooled.buffer(256);
        buffer.setByte(0, (byte) 1);
        buffer.writerIndex(1);

        Reflection.sendPacket(player, new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buffer)));

        player.getInventory().setItemInHand(previous);
    }

    /**
     * Format a given object into a formatted
     * string
     *
     * @param stack Stack
     *
     * @return Formatted string
     */
    public static String stackToStr(ItemStack stack)
    {
        return stack.getType().name() + ", " + stack.getAmount() + ", " + stack.getDurability();
    }

    /**
     * Format a given formatted string into
     * a object {@link ItemStack}
     *
     * @param string Formatted string
     *
     * @return Stack
     */
    public static ItemStack strToStack(String string)
    {
        String[] data = string.split(", ");
        return new ItemStack(Material.matchMaterial(data[0]), Integer.parseInt(data[1]), Short.parseShort(data[2]));
    }

    /**
     * Hide all the special attributes of an
     * {@link ItemStack}
     *
     * @param itemStack The stack
     * @return Cleaned stack
     */
    public static ItemStack hideAllAttributes(ItemStack itemStack)
    {
        ItemMeta meta = itemStack.getItemMeta();

        for (ItemFlag itemFlag : ItemFlag.values())
            if (itemFlag.name().startsWith("HIDE_"))
                meta.addItemFlags(itemFlag);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Get the given player's username head
     *
     * @param player Player's username
     *
     * @return Player's head
     */
    public static ItemStack getPlayerHead(String player)
    {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player);
        head.setItemMeta(meta);

        return head;
    }

    /**
     * Create a player head with a base64 encoded texture
     *
     * @param texture Base64 texture
     *
     * @return A custom head with your texture
     */
    public static ItemStack getCustomHead(String texture)
    {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();

        try
        {
            Reflection.setValue(headMeta, "profile", getHeadCustomizedGameProfile(texture));
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        head.setItemMeta(headMeta);
        return head;
    }

    /**
     * Create a {@link GameProfile} instance with a base64 encoded texture
     *
     * @param texture Base64 texture
     *
     * @return A custom {@link GameProfile} instance with your texture
     */
    public static GameProfile getHeadCustomizedGameProfile(String texture)
    {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();

        if (propertyMap == null)
            throw new IllegalStateException("Profile doesn't contain a property map");

        byte[] encodedData = texture.getBytes();
        propertyMap.put("textures", new Property("textures", new String(encodedData)));

        return profile;
    }
}
