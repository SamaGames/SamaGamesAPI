package net.samagames.api.stats.games;

import java.sql.Timestamp;

/**
 * Created by Silvanosky on 15/03/2016.
 */
public interface IQuakeStats {

    // Getters
    int getDeaths();

    int getKills();

    int getPlayedGames();

    int getWins();

    Timestamp getCreationDate();

    Timestamp getUpdateDate();

    long getPlayedTime();

    // Setters
    void setDeaths(int deaths);

    void setKills(int kills);

    void setPlayedGames(int playedGames);

    void setWins(int wins);

    void setCreationDate(Timestamp creationDate);

    void setUpdateDate(Timestamp updateDate);

    void setPlayedTime(long playedTime);

    void update();

    void refresh();
}
