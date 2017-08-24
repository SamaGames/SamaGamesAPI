package net.samagames.api.stats;

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
public class Leaderboard
{
    private final PlayerStatData first, second, third;

    /**
     * Constructor
     *
     * @param first First player into the leaderboard {@link PlayerStatData}
     * @param second Second player into the leaderboard {@link PlayerStatData}
     * @param third Third player into the leaderboard {@link PlayerStatData}
     */
    public Leaderboard(PlayerStatData first, PlayerStatData second, PlayerStatData third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Get the first player
     *
     * @return First player
     */
    public PlayerStatData getFirst()
    {
        return this.first;
    }

    /**
     * Get the second player
     *
     * @return Second player
     */
    public PlayerStatData getSecond()
    {
        return this.second;
    }

    /**
     * Get the third player
     *
     * @return Third player
     */
    public PlayerStatData getThird()
    {
        return this.third;
    }

    public static class PlayerStatData{
        private String name;
        private int score;
        public PlayerStatData(String name, int score)
        {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
