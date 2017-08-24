package net.samagames.api.games.themachine.messages.templates;

import net.samagames.api.games.pearls.Pearl;
import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class EarningMessageTemplate
{
    /**
     * Prepare a message to show how many
     * coins and the pearl have been earned
     *
     * @param coins Coins earned
     * @param pearl The pearl earned, null if none
     *
     * @return Formatted lines
     */
    public List<String> prepare(int coins, Pearl pearl)
    {
        List<String> finalLines = new ArrayList<>();
        finalLines.add(ChatUtils.getCenteredText(ChatColor.WHITE + "•" + ChatColor.BOLD + " Récompenses " + ChatColor.RESET + ChatColor.WHITE + "•"));
        finalLines.add("");
        finalLines.add(ChatUtils.getCenteredText(ChatColor.GOLD + "Vous avez gagné " + coins + (coins == 1 ? " pièce !" : " pièces !")));
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
     * and the pearl have been earned to a given player
     *
     * @param player Player to send the message
     * @param coins Coins earned
     * @param pearl Pearl earned
     */
    public void execute(Player player, int coins, Pearl pearl)
    {
        new BasicMessageTemplate().prepare(this.prepare(coins, pearl)).forEach(player::sendMessage);
    }
}
