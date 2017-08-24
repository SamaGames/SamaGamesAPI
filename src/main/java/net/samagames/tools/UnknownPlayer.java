package net.samagames.tools;

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
public class UnknownPlayer
{
    private UUID playerId;
    private String playerName;

    /**
     * Constructor
     *
     * @param playerId Player's UUID
     * @param playerName Player's username
     */
    public UnknownPlayer(UUID playerId, String playerName)
    {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    /**
     * Constructor
     *
     * @param player Player
     */
    public UnknownPlayer(Player player)
    {
        this(player.getUniqueId(), player.getName());
    }

    /**
     * Constructor
     *
     * @param playerId Player's UUID
     */
    public UnknownPlayer(UUID playerId)
    {
        this(playerId, null);
    }

    /**
     * Set player's UUID
     *
     * @param playerId Player's UUID
     */
    public void setPlayerId(UUID playerId)
    {
        this.playerId = playerId;
    }

    /**
     * Set player's username
     *
     * @param playerName Player's username
     */
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    /**
     * Get player's UUID
     *
     * @return Player's UUID
     */
    public UUID getPlayerId() {
        return this.playerId;
    }

    /**
     * Get player's username
     *
     * @return Player's username
     */
    public String getPlayerName() {
        return this.playerName;
    }

    @Override
    public String toString() {
        return this.playerId + "|" + this.playerName;
    }

    @Override
    public boolean equals(Object compare)
    {
        if (compare instanceof UnknownPlayer)
            return ((UnknownPlayer) compare).getPlayerId().equals(this.playerId);
        else
            return compare instanceof UUID && compare.equals(this.playerId);
    }
}
