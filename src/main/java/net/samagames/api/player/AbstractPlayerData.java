package net.samagames.api.player;

import java.util.Date;
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
public abstract class AbstractPlayerData
{
    /**
     * Get the custom name of the player (/nick)
     *
     * @return Custom name
     */
    public abstract String getCustomName();

    /**
     * Get the current effective name of the player
     *
     * @return Effective name
     */
    public abstract String getEffectiveName();
    /**
     * Get player's UUID
     *
     * @return UUID
     */
    public abstract UUID getPlayerID();

    /**
     * Get last refresh date
     *
     * @return Last time
     */
    public abstract Date getLastRefresh();

    /**
     * ========================
     * > Coins management
     * ========================
     */

    /**
     * Credit the coins number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     * @param applyMultiplier Have to apply multiplier
     * @param financialCallback Callback fired after the process
     */
    public abstract void creditCoins(long amount, String reason, boolean applyMultiplier, IFinancialCallback financialCallback);

    /**
     * Withdraw the coins number of the player
     *
     * @param amount Amount to withdraw
     * @param financialCallback Callback fired after the process
     */
    public abstract void withdrawCoins(long amount, IFinancialCallback financialCallback);

    /**
     * Credit the coins number of the player
     *
     * @param incrBy Amount to credit
     *
     * @return The new count
     */
    public abstract long increaseCoins(long incrBy);

    /**
     * Withdraw the coins number of the player
     *
     * @param decrBy Amount to withdraw
     *
     * @return The new count
     */
    public abstract long decreaseCoins(long decrBy);

    /**
     * Credit the coins number of the player
     * 
     * @param amount Amount to credit
     * @param reason Credit's reason
     */
    public void creditCoins(long amount, String reason)
    {
        this.creditCoins(amount, reason, true, null);
    }
    
    /**
     * Credit the coins number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     * @param applyMultiplier Have to apply multiplier
     */
    public void creditCoins(long amount, String reason, boolean applyMultiplier)
    {
        this.creditCoins(amount, reason, applyMultiplier, null);
    }

    /**
     * Credit the coins number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     * @param financialCallback Callback fired after the process
     */
    public void creditCoins(long amount, String reason, IFinancialCallback financialCallback)
    {
        this.creditCoins(amount, reason, true, financialCallback);
    }

    /**
     * Withdraw the coins number of the player
     *
     * @param amount Amount to withdraw
     */
    public void withdrawCoins(long amount)
    {
        this.withdrawCoins(amount, null);
    }

    /**
     * Get current coins number of the player
     * 
     * @return Number of coins
     */
    public abstract long getCoins();

    /**
     * Is the player has current coins
     * 
     * @param amount Coins number to check
     *
     * @return {@code true} is has enough
     */
    public boolean hasEnoughCoins(long amount)
    {
        return this.getCoins() >= amount;
    }


    /**
     * ========================
     * > Stars management
     * ========================
     */

    /**
     * Credit the stars number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     * @param applyMultiplier Have to apply multiplier
     * @param financialCallback Callback fired after the process
     */
    @Deprecated
    public abstract void creditStars(long amount, String reason, boolean applyMultiplier, IFinancialCallback financialCallback);

    /**
     * Withdraw the stars number of the player
     *
     * @param amount Amount to withdraw
     * @param financialCallback Callback fired after the process
     */
    @Deprecated
    public abstract void withdrawStars(long amount, IFinancialCallback financialCallback);

    /**
     * Credit the stars number of the player
     *
     * @param incrBy Amount to credit
     *
     * @return The new count
     */
    @Deprecated
    public abstract long increaseStars(long incrBy);

    /**
     * Withdraw the stars number of the player
     *
     * @param decrBy Amount to withdraw
     *
     * @return The new count
     */
    @Deprecated
    public abstract long decreaseStars(long decrBy);

    /**
     * Credit the stars number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     */
    @Deprecated
    public void creditStars(long amount, String reason)
    {
        this.creditStars(amount, reason, true, null);
    }

    /**
     * Credit the stars number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     * @param applyMultiplier Have to apply multiplier
     */
    @Deprecated
    public void creditStars(long amount, String reason, boolean applyMultiplier)
    {
        this.creditStars(amount, reason, applyMultiplier, null);
    }

    /**
     * Credit the stars number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     * @param financialCallback Callback fired after the process
     */
    @Deprecated
    public void creditStars(long amount, String reason, IFinancialCallback financialCallback)
    {
        this.creditStars(amount, reason, true, financialCallback);
    }

    /**
     * Withdraw the stars number of the player
     *
     * @param amount Amount to withdraw
     */
    @Deprecated
    public void withdrawStars(long amount)
    {
        this.withdrawStars(amount, null);
    }

    /**
     * Get current stars number of the player
     *
     * @return Number of stars
     */
    @Deprecated
    public abstract long getStars();


    /**
     * ========================
     * > Powders management
     * ========================
     */

    /**
     * Credit the powders number of the player
     *
     * @param amount Amount to credit
     * @param financialCallback Callback fired after the process
     */
    public abstract void creditPowders(long amount, IFinancialCallback financialCallback);

    /**
     * Withdraw the powders number of the player
     *
     * @param amount Amount to withdraw
     * @param financialCallback Callback fired after the process
     */
    public abstract void withdrawPowders(long amount, IFinancialCallback financialCallback);

    /**
     * Credit the powders number of the player
     *
     * @param incrBy Amount to credit
     *
     * @return The new count
     */
    public abstract long increasePowders(long incrBy);

    /**
     * Withdraw the powders number of the player
     *
     * @param decrBy Amount to withdraw
     *
     * @return The new count
     */
    public abstract long decreasePowders(long decrBy);

    /**
     * Get current powders number of the player
     *
     * @return Number of powders
     */
    public abstract long getPowders();

    /**
     * Is the player has current stars
     *
     * @param amount Stars number to check
     *
     * @return {@code true} is has enough
     */
    @Deprecated
    public boolean hasEnoughStars(long amount)
    {
        return this.getStars() >= amount;
    }

    /**
     * Handle the displayname according to nickname or real name
     * @return
     */
    public abstract String getDisplayName();

    /**
     * Return true if the player has a nickname
     * @return
     */
    public abstract boolean hasNickname();

}