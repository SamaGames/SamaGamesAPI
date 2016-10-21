package net.samagames.api.games.pearls;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 21/10/2016
 */
public class Pearl
{
    private final UUID uuid;
    private final int stars;
    private final long expiration;

    public Pearl(UUID uuid, int stars, long expiration)
    {
        this.uuid = uuid;
        this.stars = stars;
        this.expiration = expiration;
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    public int getStars()
    {
        return this.stars;
    }

    public long getExpiration()
    {
        return this.expiration;
    }

    public long getExpirationInDays()
    {
        return TimeUnit.MILLISECONDS.toDays(this.expiration - System.currentTimeMillis());
    }
}
