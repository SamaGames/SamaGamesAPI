package net.samagames.api.stats;

import net.samagames.api.SamaGamesAPI;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class PlayerStat
{
    private final UUID playerUUID;
    private final String game;
    private final String stat;
    private double value;
    private Long rank;

    public PlayerStat(UUID playerUUID, String game, String stat)
    {
        this.playerUUID = playerUUID;
        this.game = game;
        this.stat = stat;
    }

    public void fill()
    {
        Jedis jedis = SamaGamesAPI.get().getResource();

        try
        {
            this.value = jedis.zscore("gamestats:" + this.game + ":" + this.stat, this.playerUUID.toString());
            this.rank = jedis.zrank("gamestats:" + this.game + ":" + this.stat, this.playerUUID.toString());

            if (this.rank != null)
                this.rank++;

        }
        finally
        {
            jedis.close();
        }
    }

    public UUID getPlayerUUID()
    {
        return this.playerUUID;
    }

    public String getGame()
    {
        return this.game;
    }

    public String getStat()
    {
        return this.stat;
    }

    public long getRank()
    {
        return this.rank;
    }

    public double getValue()
    {
        return this.value;
    }
}
