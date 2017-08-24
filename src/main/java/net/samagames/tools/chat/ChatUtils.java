package net.samagames.tools.chat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.util.ChatPaginator;

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
