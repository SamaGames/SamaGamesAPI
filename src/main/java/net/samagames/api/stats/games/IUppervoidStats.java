package net.samagames.api.stats.games;

import java.sql.Timestamp;

/**
 * Created by Silvanosky on 15/03/2016.
 */
public interface IUppervoidStats {

    int getBlocks();

    int getGrenades();

    int getKills();

    int getPlayedGames();

    int getTntLaunched();

    int getWins();

    Timestamp getCreationDate();

    Timestamp getUpdateDate();

    long getPlayedTime();

    // Setters
    void setBlocks(int blocks);

    void setGrenades(int grenades);

    void setKills(int kills);

    void setPlayedGames(int playedGames);

    void setTntLaunched(int tntLaunched);

    void setWins(int wins);

    void setCreationDate(Timestamp creationDate);

    void setUpdateDate(Timestamp updateDate);

    void setPlayedTime(long playedTime);

    void update();

    void refresh();

}
