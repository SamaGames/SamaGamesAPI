package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.PlayerUtils;
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
public class PlayerLeaderboardWinTemplate
{
    /**
     * Prepare a message to display a leaderboard with
     * given players
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     *
     * @return Formatted lines
     */
    public List<String> prepare(Player winner, Player second, Player third)
    {
        List<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second)));

        if(third != null)
            lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third)));
        
        return lines;
    }

    /**
     * Prepare a message to display a leaderboard with
     * given players and their respective scores
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     * @param winnerScore Winner's score
     * @param secondScore Second's score
     * @param thirdScore Third's score
     *
     * @return Formatted lines
     */
    public List<String> prepare(Player winner, Player second, Player third, int winnerScore, int secondScore, int thirdScore)
    {
        List<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second) + ChatColor.GRAY + " (" + secondScore + ")"));

        if(third != null)
            lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third) + ChatColor.GRAY + " (" + thirdScore + ")"));

        return lines;
    }

    /**
     * Prepare a message to display a leaderboard with
     * given players and a commentary for the winner
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     * @param commentary Commentary
     *
     * @return Formatted lines
     */
    public List<String> prepare(Player winner, Player second, Player third, String commentary)
    {
        List<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));

        if(commentary != null)
            lines.add(ChatUtils.getCenteredText(commentary));

        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner)));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second)));

        if(third != null)
            lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third)));

        return lines;
    }

    /**
     * Prepare a message to display a leaderboard with
     * given players, their respective scores and a
     * commentary for the winner
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     * @param commentary Commentary
     * @param winnerScore Winner's score
     * @param secondScore Second's score
     * @param thirdScore Third's score
     *
     * @return Formatted lines
     */
    public List<String> prepare(Player winner, Player second, Player third, String commentary, int winnerScore, int secondScore, int thirdScore)
    {
        List<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));

        if(commentary != null)
            lines.add(ChatUtils.getCenteredText(commentary));

        lines.add("");
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "1er" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(winner) + ChatColor.GRAY + " (" + winnerScore + ")"));
        lines.add(ChatUtils.getCenteredText(ChatColor.YELLOW + "2e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(second) + ChatColor.GRAY + " (" + secondScore + ")"));

        if(third != null)
            lines.add(ChatUtils.getCenteredText(ChatColor.RED + "3e" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(third) + ChatColor.GRAY + " (" + secondScore + ")"));
        return lines;
    }

    /**
     * Send a message to display a leaderboard with
     * given players
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     */
    public void execute(Player winner, Player second, Player third)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third));
    }

    /**
     * Send a message to display a leaderboard with
     * given players and their respective scores
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     * @param winnerScore Winner's score
     * @param secondScore Second's score
     * @param thirdScore Third's score
     */
    public void execute(Player winner, Player second, Player third, int winnerScore, int secondScore, int thirdScore)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third, winnerScore, secondScore, thirdScore));
    }

    /**
     * Send a message to display a leaderboard with
     * given players and a commentary for the winner
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     * @param commentary Commentary
     */
    public void execute(Player winner, Player second, Player third, String commentary)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third, commentary));
    }

    /**
     * Send a message to display a leaderboard with
     * given players, their respective scores and a
     * commentary for the winner
     *
     * @param winner Winner in the leaderboard
     * @param second Second in the leaderboard
     * @param third Third in the leaderboard (can be null)
     * @param commentary Commentary
     * @param winnerScore Winner's score
     * @param secondScore Second's score
     * @param thirdScore Third's score
     */
    public void execute(Player winner, Player second, Player third, String commentary, int winnerScore, int secondScore, int thirdScore)
    {
        new WinMessageTemplate().execute(this.prepare(winner, second, third, commentary, winnerScore, secondScore, thirdScore));
    }
}
