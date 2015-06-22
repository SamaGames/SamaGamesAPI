package net.samagames.core.api.stats;

import net.samagames.api.stats.PlayerStat;

public class Leaderboard
{
    private final PlayerStat first;
    private final PlayerStat second;
    private final PlayerStat third;

    public Leaderboard(PlayerStat first, PlayerStat second, PlayerStat third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public PlayerStat getFirst()
    {
        return this.first;
    }

    public PlayerStat getSecond()
    {
        return this.second;
    }

    public PlayerStat getThird()
    {
        return this.third;
    }
}
