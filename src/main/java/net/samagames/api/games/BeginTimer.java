package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Titles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Game's begin timer class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class BeginTimer implements Runnable
{
    private int timeStart = 30;
    private final Game game;
    private final SamaGamesAPI api;
    private int time;
    private boolean ready;

    /**
     * Constructor
     *
     * @param game Game
     */
    public BeginTimer(Game game)
    {
        this.game = game;
        this.api = SamaGamesAPI.get();
        this.time = timeStart;
        this.ready = false;
    }

    /**
     * Timer loop called every second.
     *
     * It calculate the time remaining before the game starts.
     * Messages and Titles we'll be displayed to the players
     * depending of the time remaining.
     *
     * Also, the XP level of the player we'll be modified with
     * the number of seconds remaining.
     */
    @Override
    public void run()
    {
        int nPlayers = this.game.getConnectedPlayers();

        if (nPlayers >= this.api.getGameManager().getGameProperties().getMinSlots() && !this.ready)
        {
            this.ready = true;
            this.game.setStatus(Status.READY_TO_START);
            this.time = this.timeStart;
        }

        if (nPlayers < this.api.getGameManager().getGameProperties().getMinSlots() && this.ready)
        {
            this.ready = false;
            this.game.setStatus(Status.WAITING_FOR_PLAYERS);

            this.api.getGameManager().getCoherenceMachine().getMessageManager().writeNotEnougthPlayersToStart();
            
            for (Player p : Bukkit.getOnlinePlayers())
                p.setLevel(this.timeStart);
        }

        if (this.ready)
        {
            this.time--;

            if((this.time < 5 && this.time > 0) || (this.time > 5 && this.time % 10 == 0))
                this.api.getGameManager().getCoherenceMachine().getMessageManager().writeGameStartIn(this.time);

            if(this.time <= 5 && this.time > 0)
                for (Player player : Bukkit.getOnlinePlayers())
                    Titles.sendTitle(player, 0, 22, 0, ChatColor.RED + "" + ChatColor.BOLD + this.time, ChatColor.YELLOW + this.api.getGameManager().getCoherenceMachine().getStartCountdownCatchPhrase());

            this.sendSound(this.time);
            
            if(this.time <= 0)
            {
                Bukkit.getScheduler().runTask(this.api.getPlugin(), () ->
                {
                    try
                    {
                        this.game.startGame();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });

                this.game.getBeginTimer().cancel();
            }
        }
    }

    /**
     *
     * @param timeStart
     */
    public void setTimeStart(int timeStart)
    {
        this.timeStart = timeStart;
    }

    /**
     * Send a piano note to the players if the remaining seconds are
     * belows or equals 5.
     *
     * @param seconds Actual remaining seconds
     */
    private void sendSound(int seconds)
    {
        boolean ring = false;
        
        if (seconds <= 5 && seconds != 0)
        {
            ring = true;
        }
        
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.setLevel(seconds);
            
            if (ring)
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);

            if (seconds == 0)
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
        }
    }

    /**
     * Returns the amount of seconds left before the beginning of the game.
     *
     * @return The seconds left; the maximal waiting time if the counter is not started.
     * Use {@link #isTimerRunning()} to differentiate these cases.
     */
    public int getSecondsLeft()
    {
        return this.time;
    }

    /**
     * Checks if the timer is currently running, i.e. if the amount of players connected
     * is above the minimal amount required to start the game, but the game is not started
     * yet.
     *
     * @return {@code true} if the timer is running.
     */
    public boolean isTimerRunning()
    {
        return this.ready && this.time > 0;
    }
}
