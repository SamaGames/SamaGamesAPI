package net.samagames.api.games.themachine.messages.templates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

/**
 * Basic message template class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class BasicMessageTemplate
{
    /**
     * Prepare a empty message with spacers and given
     * content
     *
     * @param lines Content of the message
     *
     * @return Formatted lines
     */
    public ArrayList<String> prepare(ArrayList<String> lines)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        finalLines.add(ChatColor.GOLD + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        finalLines.addAll(lines);
        finalLines.add(ChatColor.GOLD + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        
        return finalLines;
    }

    /**
     * Send a empty message with spacers and given
     * content
     *
     * @param lines Content of the message
     */
    public void execute(ArrayList<String> lines)
    {
        this.prepare(lines).forEach(Bukkit::broadcastMessage);
    }
}
