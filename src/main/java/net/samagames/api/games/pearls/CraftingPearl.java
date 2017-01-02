package net.samagames.api.games.pearls;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 12/11/2016
 */
public class CraftingPearl
{
    private final UUID uuid;
    private final int stars;
    private final long createdAt;

    public CraftingPearl(UUID uuid, int stars, long createdAt)
    {
        this.uuid = uuid;
        this.stars = stars;
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

    public long getCreation()
    {
        return this.createdAt;
    }

    public long getCreationInMinutes()
    {
        return TimeUnit.MILLISECONDS.toMinutes(this.createdAt - System.currentTimeMillis());
    }
}
