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
    private final long expireAt;
    private final long createdAt;

    public Pearl(UUID uuid, int stars, long expireAt, long createdAt)
    {
        this.uuid = uuid;
        this.stars = stars;
        this.expireAt = expireAt;
        this.createdAt = createdAt;
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
        return this.expireAt;
    }

    public long getCreation()
    {
        return this.createdAt;
    }

    public long getExpirationInDays()
    {
        return TimeUnit.MILLISECONDS.toDays(this.expireAt - System.currentTimeMillis());
    }

    public long getCreationInMinutes()
    {
        return TimeUnit.MILLISECONDS.toMinutes(this.createdAt - System.currentTimeMillis());
    }
}
