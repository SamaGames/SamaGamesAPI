package net.samagames.tools.chat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.util.ChatPaginator;

/**
 * Chat utils
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class ChatUtils
{
    /**
     * Add space to a given text to center it
     * into the chat
     *
     * @param base Base text
     *
     * @return Formatted text
     */
    public static String getCenteredText(String base)
    {
        StringBuilder builder = new StringBuilder();
        int startPos = getPosToWriteCentered(ChatColor.stripColor(base));
        
        for(int i = 0; i < startPos; i++)
        {
            builder.append(" ");
        }
        
        builder.append(base);
        
        return builder.toString();
    }

    /**
     * Get number of space to add to center the
     * given text
     *
     * @param text Base text
     *
     * @return Number of space
     */
    public static int getPosToWriteCentered(String text)
    {
        return (ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH / 2) - (text.length() / 2);
    }
}
