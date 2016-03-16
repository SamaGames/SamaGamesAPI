package net.samagames.api.stats.games;

import java.sql.Timestamp;

/**
 * Created by Silvanosky on 15/03/2016.
 */
public interface IJukeBoxStats {

    int getMehs();

    int getWoots();

    Timestamp getCreationDate();

    Timestamp getUpdateDate();

    long getPlayedTime();

    void setMehs(int mehs);

    void setWoots(int woots);

    void setCreationDate(Timestamp creationDate);

    void setUpdateDate(Timestamp updateDate);

    void setPlayedTime(long playedTime);

    void update();

    void refresh();
}
