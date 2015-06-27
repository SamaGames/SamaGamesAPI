package net.samagames.api.games;

public interface IGamePlayer
{
    void handleLogin(boolean reconnect);
    void handleLogout();

    void addCoins(int coins, String reason);
    void addStars(int stars, String reason);
    void setSpectator();

    int getCoins();
    int getStars();

    boolean isSpectator();
}
