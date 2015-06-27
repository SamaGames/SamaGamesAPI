package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.IGamePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer implements IGamePlayer
{
    private final UUID uuid;

    private int coins;
    private int stars;
    private boolean spectator;

    public GamePlayer(Player player)
    {
        this.uuid = player.getUniqueId();

        this.coins = 0;
        this.stars = 0;
        this.spectator = false;
    }

    public void handleLogin(boolean reconnect)
    {

    }

    public void handleLogout() {}

    public void addCoins(int coins, String reason)
    {
        this.coins += coins;
        SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid).creditCoins(coins, reason, true);
    }

    public void addStars(int stars, String reason)
    {
        this.stars += stars;
        SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid).creditStars(stars, reason, false);
    }

    public void setSpectator()
    {
        this.spectator = true;
    }

    public int getCoins()
    {
        return this.coins;
    }

    public int getStars()
    {
        return this.stars;
    }

    public boolean isSpectator()
    {
        return this.spectator;
    }
}
