package net.samagames.api.stats;

/**
 * Leaderboard class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class Leaderboard
{
    private final IPlayerStat first, second, third;

    /**
     * Constructor
     *
     * @param first First player into the leaderboard {@link IPlayerStat}
     * @param second Second player into the leaderboard {@link IPlayerStat}
     * @param third Third player into the leaderboard {@link IPlayerStat}
     */
    public Leaderboard(IPlayerStat first, IPlayerStat second, IPlayerStat third)
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
    public IPlayerStat getFirst()
    {
        return this.first;
    }

    /**
     * Get the second player
     *
     * @return Second player
     */
    public IPlayerStat getSecond()
    {
        return this.second;
    }

    /**
     * Get the third player
     *
     * @return Third player
     */
    public IPlayerStat getThird()
    {
        return this.third;
    }
}
