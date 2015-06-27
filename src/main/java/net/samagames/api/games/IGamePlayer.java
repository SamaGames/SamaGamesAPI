package net.samagames.api.games;

public interface IGamePlayer
{
    void handleLogin(boolean reconnect);
    void handleLogout();

    void addCoins(int coins);
    void addStars(int stars);
    void setSpectator();

    int getCoins();
    int getStars();

    boolean isSpectator();
}
