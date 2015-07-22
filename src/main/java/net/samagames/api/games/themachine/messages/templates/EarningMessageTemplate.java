package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EarningMessageTemplate
{
    public ArrayList<String> prepare(int coins, int stars)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "•" + ChatColor.BOLD + " Récompenses " + ChatColor.RESET + ChatColor.WHITE + "•"));
        finalLines.add("");
        finalLines.add(ChatUtils.getCenteredText(ChatColor.GOLD + "Vous avez gagné " + coins + " pièces !"));
        finalLines.add(ChatUtils.getCenteredText(ChatColor.AQUA + "Vous avez gagné " + stars + " étoiles !"));
        finalLines.add("");

        return finalLines;
    }

    public void execute(Player player, int coins, int stars)
    {
        new BasicMessageTemplate().prepare(this.prepare(coins, stars)).forEach(player::sendMessage);
    }
}
