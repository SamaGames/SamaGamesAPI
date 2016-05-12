package net.samagames.api.stats;

/**
 * Leaderboard class
 *
 * Copyright (c) for SamaGames
 * All right reserved
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

    public class PlayerStatData{
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
