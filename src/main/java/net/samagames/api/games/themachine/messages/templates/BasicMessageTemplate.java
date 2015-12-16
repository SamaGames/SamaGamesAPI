package net.samagames.api.games.themachine.messages.templates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> prepare(List<String> lines)
    {
        List<String> finalLines = new ArrayList<>();
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
    public void execute(List<String> lines)
    {
        this.prepare(lines).forEach(Bukkit::broadcastMessage);
    }
}
