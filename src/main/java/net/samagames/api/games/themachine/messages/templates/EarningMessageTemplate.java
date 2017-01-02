package net.samagames.api.games.themachine.messages.templates;

import net.samagames.api.games.pearls.Pearl;
import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
     *              @param pearl The pearl earned, null if none
     *
     * @return Formatted lines
     */
    public List<String> prepare(int coins, int stars, Pearl pearl)
    {
        List<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "•" + ChatColor.BOLD + " Récompenses " + ChatColor.RESET + ChatColor.WHITE + "•"));
        finalLines.add("");
        finalLines.add(ChatUtils.getCenteredText(ChatColor.GOLD + "Vous avez gagné " + coins + (coins == 1 ? " pièce !" : " pièces !")));
        finalLines.add(ChatUtils.getCenteredText(ChatColor.AQUA + "Vous avez gagné " + stars + (stars == 1 ? " étoile !" : " étoiles !")));
        finalLines.add("");

        if (pearl != null)
        {
            finalLines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Vous avez gagné une perle de " + ChatColor.AQUA + "niveau " + pearl.getStars() + ChatColor.GREEN + "!"));
            finalLines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Echangez-là auprès de " + ChatColor.GOLD + "Graou" + ChatColor.GREEN + " dans le Hub !"));
            finalLines.add("");
        }

        return finalLines;
    }

    /**
     * Prepare a message to show how many coins
     * and stars have been earned to a given player
     *
     * @param coins Coins earned
     * @param stars Stars earned
     */
    public void execute(Player player, int coins, int stars, Pearl pearl)
    {
        new BasicMessageTemplate().prepare(this.prepare(coins, stars, pearl)).forEach(player::sendMessage);
    }
}
