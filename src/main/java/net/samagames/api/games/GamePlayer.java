package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.AbstractPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer
{
    protected final UUID uuid;

    protected int coins;
    protected int stars;
    protected boolean spectator;

    public GamePlayer(Player player)
    {
        this.uuid = player.getUniqueId();

        this.coins = 0;
        this.stars = 0;
        this.spectator = false;
    }

    public void handleLogin(boolean reconnect)
    {
        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeWelcomeInGameToPlayer(this.getPlayerIfOnline());
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

        Bukkit.getScheduler().runTask(SamaGamesAPI.get().getPlugin(), () -> this.getPlayerIfOnline().setGameMode(GameMode.SPECTATOR));
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    public Player getPlayerIfOnline()
    {
        return Bukkit.getPlayer(this.uuid);
    }

    public AbstractPlayerData getPlayerData()
    {
        return SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid);
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
