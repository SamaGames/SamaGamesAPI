package net.samagames.tools;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
public class Titles
{
    /**
     * Send title
     *
     * @param player Player
     * @param fadeIn Fade in time (in ticks)
     * @param stay Stay time (in ticks)
     * @param fadeOut Fade out time (in ticks)
     * @param title Title text
     * @param subtitle Subtitle text
     */
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
    {
        Reflection.sendPacket(player, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut));

        if (subtitle != null)
        {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

            Reflection.sendPacket(player, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}")));
        }

        if (title != null)
        {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);

            Reflection.sendPacket(player, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}")));
        }
    }

    /**
     * Send tab title
     *
     * @param player Player
     * @param header Header line
     * @param footer Footer line
     */
    public static void sendTabTitle(Player player, String header, String footer)
    {
        if (header == null) header = "";
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null) footer = "";
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());

        try
        {
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            Reflection.setField(packet.getClass().getDeclaredField("a"), packet, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}"));
            Reflection.setField(packet.getClass().getDeclaredField("b"), packet, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}"));

            Reflection.sendPacket(player, packet);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }
}