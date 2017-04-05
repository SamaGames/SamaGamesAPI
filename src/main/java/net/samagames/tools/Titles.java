package net.samagames.tools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Titles utils class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Titles
{
    private static Class<?> iChatBaseComponentClass;
    private static Class<?> packetPlayOutTitleClass;
    private static Class<?> packetPlayOutPlayerListHeaderFooterClass;
    private static Class<?> enumTitleActionClass;
    private static Class<?> chatSerializerClass;

    private static Method fromJsonMethod;

    private static Field timesActionField;
    private static Field subtitleActionField;
    private static Field titleActionField;
    private static Field subtitlePacketField;

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
        try
        {
            Object packet = packetPlayOutTitleClass.getDeclaredConstructor(enumTitleActionClass, iChatBaseComponentClass, int.class, int.class, int.class).newInstance(timesActionField.get(null), null, fadeIn, stay, fadeOut);

            Reflection.sendPacket(player, packet);

            if (subtitle != null)
            {
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

                Object titleSub = fromJsonMethod.invoke(null, "{\"text\": \"" + subtitle + "\"}");
                packet = packetPlayOutTitleClass.getDeclaredConstructor(enumTitleActionClass, iChatBaseComponentClass).newInstance(subtitleActionField.get(null), titleSub);

                Reflection.sendPacket(player, packet);
            }

            if (title != null)
            {
                title = title.replaceAll("%player%", player.getDisplayName());
                title = ChatColor.translateAlternateColorCodes('&', title);

                Object titleMain = fromJsonMethod.invoke(null, "{\"text\": \"" + title + "\"}");
                packet = packetPlayOutTitleClass.getDeclaredConstructor(enumTitleActionClass, iChatBaseComponentClass).newInstance(titleActionField.get(null), titleMain);

                Reflection.sendPacket(player, packet);
            }
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
        {
            e.printStackTrace();
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
            Object tabTitle = fromJsonMethod.invoke(null, "{\"text\": \"" + header + "\"}");
            Object tabFoot = fromJsonMethod.invoke(null, "{\"text\": \"" + footer + "\"}");

            Object packet = packetPlayOutPlayerListHeaderFooterClass.getDeclaredConstructor(iChatBaseComponentClass).newInstance(tabTitle);
            subtitlePacketField.set(packet, tabFoot);

            Reflection.sendPacket(player, packet);
        }
        catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    static
    {
        try
        {
            iChatBaseComponentClass = Reflection.getNMSClass("IChatBaseComponent");
            packetPlayOutTitleClass = Reflection.getNMSClass("PacketPlayOutTitle");
            packetPlayOutPlayerListHeaderFooterClass = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
            enumTitleActionClass = Reflection.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
            chatSerializerClass = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer");

            fromJsonMethod = chatSerializerClass.getMethod("a", String.class);

            timesActionField = enumTitleActionClass.getField("TIMES");
            subtitleActionField = enumTitleActionClass.getField("SUBTITLE");
            titleActionField = enumTitleActionClass.getField("TITLE");
            subtitlePacketField = packetPlayOutPlayerListHeaderFooterClass.getDeclaredField("b");

            subtitlePacketField.setAccessible(true);
        }
        catch (NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }
}