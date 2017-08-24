package net.samagames.api.games.pearls;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
