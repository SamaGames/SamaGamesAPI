package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.AbstractPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer
{
    protected final UUID uuid;

    protected int coins;
    protected int stars;
    protected boolean spectator;
    protected boolean moderator;

    public GamePlayer(Player player)
    {
        this.uuid = player.getUniqueId();

        this.coins = 0;
        this.stars = 0;
        this.spectator = false;
        this.moderator = false;
    }

    /**
     * Override this to execute something when this player logs-in.
     *
     * Please call the {@code super} method at the beginning of your own one.
     *
     * @param reconnect {@code true} if the player is reconnecting—if reconnection is allowed.
     */
    public void handleLogin(boolean reconnect)
    {
        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeWelcomeInGameToPlayer(this.getPlayerIfOnline());
    }

    /**
     * Override this to execute something when this player logs-out.
     *
     * Please call the {@code super} method at the beginning of your own one.
     */
    public void handleLogout() {}

    /**
     * Credits coins to this player.
     *
     * @param coins The amount of coins to credit.
     * @param reason The displayed reason of the credit.
     */
    public void addCoins(int coins, String reason)
    {
        this.coins += coins;
        SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid).creditCoins(coins, reason, true);
    }

    /**
     * Credits stars to this player.
     *
     * @param stars The amount of stars to credit.
     * @param reason The displayed reason of the credit.
     */
    public void addStars(int stars, String reason)
    {
        this.stars += stars;
        SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid).creditStars(stars, reason, false);
    }

    /**
     * Puts this player into spectator mode.
     */
    public void setSpectator()
    {
        this.spectator = true;

        Bukkit.getScheduler().runTask(SamaGamesAPI.get().getPlugin(), () ->
        {
            final Player bukkitPlayer = this.getPlayerIfOnline();
            if (bukkitPlayer == null)
                return;

            bukkitPlayer.setGameMode(GameMode.CREATIVE);

            for (Player player : Bukkit.getOnlinePlayers())
                player.hidePlayer(bukkitPlayer);

            bukkitPlayer.getInventory().setItem(0, SamaGamesAPI.get().getGameManager().getGame().getPlayerTracker());
            bukkitPlayer.getInventory().setItem(8, SamaGamesAPI.get().getGameManager().getCoherenceMachine().getLeaveItem());
        });
    }

    /**
     * Marks this player as a moderator. The player is also put in spectator mode.
     */
    public void setModerator()
    {
        this.moderator = true;
        this.setSpectator();
    }

    /**
     * Returns the {@link UUID} of the underlying player.
     *
     * @return The UUID.
     */
    public UUID getUUID()
    {
        return this.uuid;
    }

    /**
     * Returns the Bukkit's {@link Player} object representing the underlying player,
     * if he is online; {@code null} else.
     *
     * @return The {@link Player} object.
     */
    public Player getPlayerIfOnline()
    {
        return Bukkit.getPlayer(this.uuid);
    }

    /**
     * Returns the Bukkit's {@link OfflinePlayer} object representing the
     * underlying player.
     *
     * @return The {@link OfflinePlayer} object.
     */
    public OfflinePlayer getOfflinePlayer()
    {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    /**
     * Checks if the underlying player is currently online.
     *
     * @return {@code true} if online.
     */
    public boolean isOnline()
    {
        return getOfflinePlayer().isOnline();
    }

    /**
     * Returns the persistent data saved for this player.
     *
     * @return The data.
     */
    public AbstractPlayerData getPlayerData()
    {
        return SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid);
    }

    /**
     * Returns the coins this player earned <b>during this game</b>.
     *
     * To get the whole amount of coins this player have, use {@link AbstractPlayerData#getCoins()}.
     *
     * @return The earned coins.
     */
    public int getCoins()
    {
        return this.coins;
    }

    /**
     * Returns the stars this player earned <b>during this game</b>.
     *
     * To get the whole amount of coins this player have, use {@link AbstractPlayerData#getStars()}.
     *
     * @return The earned stars.
     */
    public int getStars()
    {
        return this.stars;
    }

    /**
     * Checks if this player is a spectator.
     *
     * @return {@code true} if spectator.
     */
    public boolean isSpectator()
    {
        return this.spectator;
    }

    /**
     * Checks if this player is a moderator.
     *
     * @return {@code true} if moderator.
     */
    public boolean isModerator()
    {
        return this.moderator;
    }
}
