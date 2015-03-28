package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class WinMessageTemplate
{
    public ArrayList<String> prepare(ArrayList<String> lines)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "" + ChatColor.BOLD + "• Résultats du jeu •"));
        finalLines.add("");
        finalLines.addAll(lines);
        finalLines.add("");
        
        return finalLines;
    }
    
    public void execute(ArrayList<String> lines)
    {
        new BasicMessageTemplate().execute(this.prepare(lines));
    }
}
