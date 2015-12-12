package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Earning message template class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class EarningMessageTemplate
{
    /**
     * Prepare a message to show how many
     * coins and stars have been earned
     *
     * @param coins Coins earned
     * @param stars Stars earned
     *
     * @return Formatted lines
     */
    public ArrayList<String> prepare(int coins, int stars)
    {
        ArrayList<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "•" + ChatColor.BOLD + " Récompenses " + ChatColor.RESET + ChatColor.WHITE + "•"));
        finalLines.add("");
        finalLines.add(ChatUtils.getCenteredText(ChatColor.GOLD + "Vous avez gagné " + coins + (coins == 1 ? " pièce !" : " pièces !")));
        finalLines.add(ChatUtils.getCenteredText(ChatColor.AQUA + "Vous avez gagné " + stars + (stars == 1 ? " étoile !" : " étoiles !")));
        finalLines.add("");

        return finalLines;
    }

    /**
     * Prepare a message to show how many coins
     * and stars have been earned to a given player
     *
     * @param coins Coins earned
     * @param stars Stars earned
     */
    public void execute(Player player, int coins, int stars)
    {
        new BasicMessageTemplate().prepare(this.prepare(coins, stars)).forEach(player::sendMessage);
    }
}
