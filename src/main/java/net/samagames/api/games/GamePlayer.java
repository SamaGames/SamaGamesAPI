package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.player.AbstractPlayerData;
import net.samagames.tools.chat.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

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
public class GamePlayer
{
    protected final UUID uuid;

    protected int coins;
    protected boolean spectator;

    protected long startTime;
    protected long playedTime;

    public GamePlayer(Player player)
    {
        this.uuid = player.getUniqueId();

        this.coins = 0;
        this.spectator = false;

        this.startTime = -1;
        this.playedTime = 0;
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
    public void handleLogout()
    {
        if (!this.spectator)
            this.stepPlayedTimeCounter();
    }

    /**
     * Credits coins to this player.
     *
     * @param coins_ The amount of coins to credit.
     * @param reason The displayed reason of the credit.
     */
    public void addCoins(int coins_, String reason)
    {
        SamaGamesAPI.get().getPlayerManager().getPlayerData(this.uuid).creditCoins(coins_, reason, true, (newAmount, difference, error) -> coins += difference);
    }

    /**
     * Ìnit the first bound of the played time counter
     * to the current time in milliseconds.
     */
    public void initPlayedTimeCounter()
    {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Compute the played time since this player login to a
     * global variable in seconds.
     */
    public void stepPlayedTimeCounter()
    {
        if (this.startTime != -1)
        {
            long stayedTime = (System.currentTimeMillis() - this.startTime) / 1000;
            this.playedTime += stayedTime;
        }
    }

    /**
     * Puts this player into spectator mode.
     */
    public void setSpectator()
    {
        this.spectator = true;
        this.stepPlayedTimeCounter();

        Bukkit.getScheduler().runTask(SamaGamesAPI.get().getPlugin(), () ->
        {
            final Player bukkitPlayer = this.getPlayerIfOnline();

            if (bukkitPlayer == null)
                return;

            bukkitPlayer.setGameMode(GameMode.SPECTATOR);

            for (Player player : Bukkit.getOnlinePlayers())
                player.hidePlayer(bukkitPlayer);

            new FancyMessage("Cliquez ").color(ChatColor.YELLOW).style(ChatColor.BOLD).then("[ICI]").command("/hub").color(ChatColor.AQUA).style(ChatColor.BOLD).then(" pour retourner au hub !").color(ChatColor.YELLOW).style(ChatColor.BOLD).send(bukkitPlayer);
        });
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
     * Returns the time this player played <b>during this game</b>
     * in seconds.
     *
     * @return The played time.
     */
    public long getPlayedTime()
    {
        return this.playedTime;
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
}
