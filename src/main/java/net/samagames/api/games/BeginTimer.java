package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Titles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BeginTimer implements Runnable
{
    private static final int timeStart = 30;
    private final Game game;
    private final SamaGamesAPI api;
    private int time;
    private boolean ready;

    public BeginTimer(Game game)
    {
        this.game = game;
        this.api = SamaGamesAPI.get();
        this.time = timeStart;
        this.ready = false;
    }

    @Override
    public void run()
    {
        int nPlayers = this.game.getConnectedPlayers();

        if (nPlayers >= api.getGameManager().getGameProperties().getMinSlots() && !this.ready)
        {
            this.ready = true;
            this.game.setStatus(Status.READY_TO_START);
            this.time = timeStart;
        }

        if (nPlayers < api.getGameManager().getGameProperties().getMinSlots() && this.ready)
        {
            this.ready = false;
            this.game.setStatus(Status.WAITING_FOR_PLAYERS);

            api.getGameManager().getCoherenceMachine().getMessageManager().writeNotEnougthPlayersToStart();
            
            for (Player p : Bukkit.getOnlinePlayers())
                p.setLevel(timeStart);
        }

        if (this.ready)
        {
            this.time--;
            double pourcentPlayer = (game.getConnectedPlayers() / api.getGameManager().getGameProperties().getMaxSlots());

            if(time > 5 && pourcentPlayer >= 0.98)
                time = 5;

            if(time < 5 || (time > 5 && time % 10 == 0))
                api.getGameManager().getCoherenceMachine().getMessageManager().writeGameStartIn(this.time);

            if(time <= 5)
                for (Player player : Bukkit.getOnlinePlayers())
                    Titles.sendTitle(player, 0, 22, 0, ChatColor.RED + "" + ChatColor.BOLD + time, ChatColor.YELLOW + api.getGameManager().getCoherenceMachine().getStartCountdownCatchPhrase());

            this.sendSound(this.time);
            
            if(this.time <= 0)
            {
                Bukkit.getScheduler().runTask(api.getPlugin(), () ->
                {
                    try
                    {
                        game.startGame();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });

                game.getBeginTimer().cancel();
            }
        }
    }

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
        return time;
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
        return ready && time > 0;
    }
}
