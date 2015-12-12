package net.samagames.api.player;

import net.samagames.api.SamaGamesAPI;

import java.util.*;

/**
 * Player data class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public abstract class AbstractPlayerData
{
    private final Map<String, String> playerData;
    private final UUID playerID;
    private Date lastRefresh;

    /**
     * Constructor
     *
     * @param playerID Player data owner UUID {@link UUID}
     */
    public AbstractPlayerData(UUID playerID)
    {
        this.playerData = new HashMap<>();
        this.playerID = playerID;
    }

    /**
     * Get the custom name of the player (/nick)
     *
     * @return Custom name
     */
    public String getCustomName()
    {
        return this.get("effectiveName");
    }

    /**
     * Get the current effective name of the player
     *
     * @return Effective name
     */
    public String getEffectiveName()
    {
        String eName = this.getCustomName();
        return (eName == null) ? SamaGamesAPI.get().getUUIDTranslator().getName(this.playerID) : eName;
    }

    /**
     * Get the current effective UUID {@link UUID} of the
     * player
     *
     * @return UUID
     */
    public UUID getEffectiveUUID()
    {
        try
        {
            return UUID.fromString(this.get("effectiveUUID", this.playerID.toString()));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Get player's UUID
     *
     * @return UUID
     */
    public UUID getPlayerID()
    {
        return this.playerID;
    }

    /**
     * Get last refresh date
     *
     * @return Last time
     */
    public Date getLastRefresh()
    {
        return this.lastRefresh;
    }

    /**
     * Get data keys
     *
     * @return List of keys
     */
    public Set<String> getKeys()
    {
        return this.playerData.keySet();
    }

    /**
     * Get player data
     *
     * @return Map of keys and values
     */
    public Map<String, String> getValues()
    {
        return this.playerData;
    }

    /**
     * Remove the player's data of the given key
     *
     * @param key Data's key
     */
    public abstract void remove(String key);

    /**
     * Define a player data
     *
     * @param key Key
     * @param value Value {@link String}
     */
    public abstract void set(String key, String value);

    /**
     * Define a player data
     *
     * @param key Key
     * @param value Value {@link Integer}
     */
    public abstract void setInt(String key, int value);

    /**
     * Define a player data
     *
     * @param key Key
     * @param value Value {@link Boolean}
     */
    public abstract void setBoolean(String key, boolean value);

    /**
     * Define a player data
     *
     * @param key Key
     * @param value Value {@link Double}
     */
    public abstract void setDouble(String key, double value);

    /**
     * Define a player data
     *
     * @param key Key
     * @param value Value {@link Long}
     */
    public abstract void setLong(String key, long value);

    /**
     * Get the value of a given key
     *
     * @param key Data's key
     *
     * @return Value or {@code null} is not found
     */
    public String get(String key)
    {
        return playerData.get(key);
    }

    /**
     * Get the value {@link String} of a given key
     *
     * @param key Data's key
     * @param def Default value
     *
     * @return Value or a default value if not found
     */
    public String get(String key, String def)
    {
        return (this.contains(key) ? this.get(key) : def);
    }

    /**
     * Get the value {@link Double} of a given key
     *
     * @param key Data's key
     *
     * @return Value or a default value if not found
     */
    public Double getDouble(String key)
    {
        String val = this.get(key);

        try
        {
            return (val == null ? null : Double.valueOf(val));
        }
        catch (Exception e)
        {
            throw new InvalidTypeException("This value is not a double.");
        }
    }

    /**
     * Get the value {@link Double} of a given key
     *
     * @param key Data's key
     * @param def Default value
     *
     * @return Value or a default value if not found
     */
    public Double getDouble(String key, double def)
    {
        Double ret = this.getDouble(key);
        return (ret == null ? def : ret);
    }

    /**
     * Get the value {@link Long} of a given key
     *
     * @param key Data's key
     *
     * @return Value or a default value if not found
     */
    public Long getLong(String key)
    {
        String val = this.get(key);

        try
        {
            return (val == null ? null : Long.valueOf(val));
        }
        catch (Exception e)
        {
            throw new InvalidTypeException("This value is not a long.");
        }
    }

    /**
     * Get the value {@link Long} of a given key
     *
     * @param key Data's key
     * @param def Default value
     *
     * @return Value or a default value if not found
     */
    public Long getLong(String key, long def)
    {
        Long ret = this.getLong(key);
        return (ret == null ? def : ret);
    }

    /**
     * Get the value {@link Integer} of a given key
     *
     * @param key Data's key
     *
     * @return Value or a default value if not found
     */
    public Integer getInt(String key)
    {
        String val = this.get(key);

        try
        {
            return (val == null ? null : Integer.valueOf(val));
        }
        catch (Exception e)
        {
            throw new InvalidTypeException("This value is not a integer.");
        }
    }

    /**
     * Get the value {@link Integer} of a given key
     *
     * @param key Data's key
     * @param def Default value
     *
     * @return Value or a default value if not found
     */
    public Integer getInt(String key, int def)
    {
        Integer ret = this.getInt(key);
        return (ret == null ? def : ret);
    }

    /**
     * Get the value {@link Boolean} of a given key
     *
     * @param key Data's key
     *
     * @return Value or a default value if not found
     */
    public Boolean getBoolean(String key)
    {
        String val = get(key);

        try
        {
            return (val == null ? null : Boolean.valueOf(val));
        }
        catch (Exception e)
        {
            throw new InvalidTypeException("This value is not a boolean.");
        }
    }

    /**
     * Get the value {@link Boolean} of a given key
     *
     * @param key Data's key
     * @param def Default value
     *
     * @return Value or a default value if not found
     */
    public Boolean getBoolean(String key, boolean def)
    {
        Boolean ret = this.getBoolean(key);
        return (ret == null ? def : ret);
    }

    /**
     * Determinate if the player's data contains a given
     * key
     *
     * @param key Key to test
     *
     * @return {@code true} if contains
     */
    public boolean contains(String key)
    {
        return this.playerData.containsKey(key);
    }


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
    public long getCoins()
    {
        return this.getLong("coins", 0L);
    }

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
    public abstract void creditStars(long amount, String reason, boolean applyMultiplier, IFinancialCallback financialCallback);

    /**
     * Withdraw the stars number of the player
     *
     * @param amount Amount to withdraw
     * @param financialCallback Callback fired after the process
     */
    public abstract void withdrawStars(long amount, IFinancialCallback financialCallback);

    /**
     * Credit the stars number of the player
     *
     * @param amount Amount to credit
     * @param reason Credit's reason
     */
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
    public void creditStars(long amount, String reason, IFinancialCallback financialCallback)
    {
        this.creditStars(amount, reason, true, financialCallback);
    }

    /**
     * Withdraw the stars number of the player
     *
     * @param amount Amount to withdraw
     */
    public void withdrawStars(long amount)
    {
        this.withdrawStars(amount, null);
    }

    /**
     * Get current stars number of the player
     *
     * @return Number of stars
     */
    public long getStars()
    {
        return this.getLong("stars", 0L);
    }

    /**
     * Is the player has current stars
     *
     * @param amount Stars number to check
     *
     * @return {@code true} is has enough
     */
    public boolean hasEnoughStars(long amount)
    {
        return this.getStars() >= amount;
    }
}