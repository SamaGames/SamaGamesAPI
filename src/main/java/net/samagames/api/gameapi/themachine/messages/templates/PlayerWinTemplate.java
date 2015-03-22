package net.samagames.api.gameapi.themachine.messages.templates;

import net.samagames.tools.PlayerUtils;
import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerWinTemplate
{
    public ArrayList<String> prepare(Player player)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player)));
        
        return lines;
    }
    
    public ArrayList<String> prepare(Player player, int score)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player) + ChatColor.GRAY + " (" + score + ")"));
        
        return lines;
    }
    
    public ArrayList<String> prepare(Player player, String commentary)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player)));
        lines.add(ChatUtils.getCenteredText(commentary));
        
        return lines;
    }
    
    public ArrayList<String> prepare(Player player, int score, String commentary)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player) + ChatColor.GRAY + " (" + score + ")"));
        lines.add(ChatUtils.getCenteredText(commentary));
        
        return lines;
    }
    
    public void execute(Player player)
    {
        new WinMessageTemplate().execute(this.prepare(player));
    }
    
    public void execute(Player player, int score)
    {
        new WinMessageTemplate().execute(this.prepare(player, score));
    }
    
    public void execute(Player player, String commentary)
    {
        new WinMessageTemplate().execute(this.prepare(player, commentary));
    }
    
    public void execute(Player player, int score, String commentary)
    {
        new WinMessageTemplate().execute(this.prepare(player, score, commentary));
    }
}