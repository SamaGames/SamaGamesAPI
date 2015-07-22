package net.samagames.api.games.themachine.messages.templates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class BasicMessageTemplate
{
    public ArrayList<String> prepare(ArrayList<String> lines)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        finalLines.add(ChatColor.GOLD + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        finalLines.addAll(lines);
        finalLines.add(ChatColor.GOLD + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        
        return finalLines;
    }
    
    public void execute(ArrayList<String> lines)
    {
        this.prepare(lines).forEach(Bukkit::broadcastMessage);
    }
}
