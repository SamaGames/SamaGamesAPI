package net.samagames.api.stats;

import net.samagames.api.games.GamesNames;

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
public interface IStatsManager
{

    void finish();
    void clearCache();

    /**
     * Get the player cached stats instance
     * player
     *
     *
     * @return IPlayerStats with all stats loaded
     */
    IPlayerStats getPlayerStats(UUID player);

    /**
     * Define if a game will be loaded at player join
     * @param game The game wanted
     * @param value The value wanted (true to set auto load for the specified games, false to disable autoload)
     */
    void setStatsToLoad(GamesNames game, boolean value);

    /**
     * Know if a game is already loaded by the manager
     * @param game The wanted game
     * @return
     */
    boolean isStatsLoading(GamesNames game);

    /**
     * Get the leaderboard of a given stat
     *
     * @param game Select game
     * @param stat Stat
     *
     * @return Leaderboard instance {@link Leaderboard}
     */
	Leaderboard getLeaderboard(GamesNames game, String stat);
}
