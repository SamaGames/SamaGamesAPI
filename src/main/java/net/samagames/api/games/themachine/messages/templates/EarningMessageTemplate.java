package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class EarningMessageTemplate
{
    public ArrayList<String> prepare(int coins, int stars)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "" + ChatColor.BOLD + "• Récompenses •"));
        finalLines.add("");
        finalLines.add(ChatColor.GOLD + "Vous avez gagné " + coins + " pièces !");
        finalLines.add(ChatColor.AQUA + "Vous avez gagné " + stars + " étoiles !");
        finalLines.add("");

        return finalLines;
    }

    public void execute(int coins, int stars)
    {
        new BasicMessageTemplate().execute(this.prepare(coins, stars));
    }
}
