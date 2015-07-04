package net.samagames.api.stats;

public class Leaderboard
{
    private final IPlayerStat first, second, third;

    public Leaderboard(IPlayerStat first, IPlayerStat second, IPlayerStat third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public IPlayerStat getFirst()
    {
        return this.first;
    }

    public IPlayerStat getSecond()
    {
        return this.second;
    }

    public IPlayerStat getThird()
    {
        return this.third;
    }
}
