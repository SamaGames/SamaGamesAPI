package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.PlayerUtils;
import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerLeaderboardWinTemplate
{
    public ArrayList<String> prepare(Player winner, Player second, Player third)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2nd" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second)));
        lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third)));
        
        return lines;
    }
    
    public ArrayList<String> prepare(Player winner, Player second, Player third, int winnerScore, int secondScore, int thirdScore)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2nd" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second) + ChatColor.GRAY + " (" + secondScore + ")"));
        lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third) + ChatColor.GRAY + " (" + thirdScore + ")"));
        
        return lines;
    }
    
    public ArrayList<String> prepare(Player winner, Player second, Player third, String commentary)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add(ChatUtils.getCenteredText(commentary));
        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2nd" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second)));
        lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third)));
        
        return lines;
    }
    
    public ArrayList<String> prepare(Player winner, Player second, Player third, String commentary, int winnerScore, int secondScore, int thirdScore)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add(ChatUtils.getCenteredText(commentary));
        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2nd" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second) + ChatColor.GRAY + " (" + secondScore + ")"));
        lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third) + ChatColor.GRAY + " (" + thirdScore + ")"));
        
        return lines;
    }
    
    public void execute(Player winner, Player second, Player third)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third));
    }
    
    public void execute(Player winner, Player second, Player third, int winnerScore, int secondScore, int thirdScore)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third, winnerScore, secondScore, thirdScore));
    }
    
    public void execute(Player winner, Player second, Player third, String commentary)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third, commentary));
    }
    
    public void execute(Player winner, Player second, Player third, String commentary, int winnerScore, int secondScore, int thirdScore)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third, commentary, winnerScore, secondScore, thirdScore));
    }
}
