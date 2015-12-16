package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Win message template class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class WinMessageTemplate
{
    /**
     * Prepare a empty win message with spacers and given
     * content
     *
     * @param lines Content of the message
     *
     * @return Formatted lines
     */
    public List<String> prepare(List<String> lines)
    {
        List<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "•" + ChatColor.BOLD + " Résultats du jeu " + ChatColor.RESET + ChatColor.WHITE + "•"));
        finalLines.add("");
        finalLines.addAll(lines);
        finalLines.add("");
        
        return finalLines;
    }

    /**
     * Send a empty win message with spacers and given
     * content
     *
     * @param lines Content of the message
     */
    public void execute(List<String> lines)
    {
        new BasicMessageTemplate().execute(this.prepare(lines));
    }
}
