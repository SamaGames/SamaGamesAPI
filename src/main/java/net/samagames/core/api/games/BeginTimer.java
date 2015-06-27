package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Status;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BeginTimer implements Runnable
{
    private final Game game;

    private int time;
    private boolean ready;

    public BeginTimer(Game game)
    {
        this.game = game;
        this.time = 120;
        this.ready = false;
    }
 
    @Override
    public void run()
    {
        int nPlayers = this.game.getConnectedPlayers();
 
        if (nPlayers >= SamaGamesAPI.get().getGameManager().getGameProperties().getMinSlots() && !this.ready)
        {
            this.ready = true;
            this.game.setStatus(Status.READY_TO_START);
            this.time = 120;
        }

        if (nPlayers < SamaGamesAPI.get().getGameManager().getGameProperties().getMinSlots() && this.ready)
        {
            this.ready = false;
            this.game.setStatus(Status.WAITING_FOR_PLAYERS);

            SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeNotEnougthPlayersToStart();
            
            for (Player p : Bukkit.getOnlinePlayers())
                p.setLevel(120);
        }

        if (this.ready)
        {
            this.time--;

            SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStartIn(this.time);
            this.sendSound(this.time);
            
            if(this.time == 0)
            {
                this.game.startGame();
            }
        }
    }

    public void sendSound(int seconds)
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
}
