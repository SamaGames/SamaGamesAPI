package net.samagames.api.games.themachine.messages.templates;

import net.samagames.tools.PlayerUtils;
import net.samagames.tools.chat.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Player win message template class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class PlayerWinTemplate
{
    /**
     * Prepare a message to display the winner of
     * the game
     *
     * @param player Winner
     *
     * @return Formatted lines
     */
    public ArrayList<String> prepare(Player player)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player)));
        
        return lines;
    }

    /**
     * Prepare a message to display the winner of
     * the game with it's score
     *
     * @param player Winner
     * @param score Winner's score
     *
     * @return Formatted lines
     */
    public ArrayList<String> prepare(Player player, int score)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player) + ChatColor.GRAY + " (" + score + ")"));
        
        return lines;
    }

    /**
     * Prepare a message to display the winner of
     * the game with a commentary
     *
     * @param player Winner
     * @param commentary Commentary
     *
     * @return Formatted lines
     */
    public ArrayList<String> prepare(Player player, String commentary)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player)));
        lines.add(ChatUtils.getCenteredText(commentary));
        
        return lines;
    }

    /**
     * Prepare a message to display the winner of
     * the game with it's score and a commentary
     *
     * @param player Winner
     * @param score Winner's score
     * @param commentary Commentary
     *
     * @return Formatted lines
     */
    public ArrayList<String> prepare(Player player, int score, String commentary)
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(ChatUtils.getCenteredText(ChatColor.GREEN + "Gagnant" + ChatColor.GRAY + " - " + ChatColor.RESET + PlayerUtils.getFullyFormattedPlayerName(player) + ChatColor.GRAY + " (" + score + ")"));
        lines.add(ChatUtils.getCenteredText(commentary));
        
        return lines;
    }

    /**
     * Send a message to display the winner of
     * the game
     *
     * @param player Winner
     */
    public void execute(Player player)
    {
        new WinMessageTemplate().execute(this.prepare(player));
    }

    /**
     * Send a message to display the winner of
     * the game with it's score
     *
     * @param player Winner
     * @param score Winner's score
     */
    public void execute(Player player, int score)
    {
        new WinMessageTemplate().execute(this.prepare(player, score));
    }

    /**
     * Send a message to display the winner of
     * the game with a commentary
     *
     * @param player Winner
     * @param commentary Commentary
     */
    public void execute(Player player, String commentary)
    {
        new WinMessageTemplate().execute(this.prepare(player, commentary));
    }

    /**
     * Send a message to display the winner of
     * the game with it's score and a commentary
     *
     * @param player Winner
     * @param score Winner's score
     * @param commentary Commentary
     */
    public void execute(Player player, int score, String commentary)
    {
        new WinMessageTemplate().execute(this.prepare(player, score, commentary));
    }
}