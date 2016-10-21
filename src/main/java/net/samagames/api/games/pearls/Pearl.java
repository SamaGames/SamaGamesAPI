package net.samagames.api.games.pearls;

import java.sql.Timestamp;
import java.util.UUID;

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
    private final Timestamp expiration;

    public Pearl(UUID uuid, int stars, Timestamp expiration)
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

    public Timestamp getExpiration()
    {
        return this.expiration;
    }
}
