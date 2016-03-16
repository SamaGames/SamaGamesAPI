package net.samagames.api.permissions;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Silvanosky on 14/03/2016.
 */
public interface IPermissionsEntity {

    UUID getUUID();

    long getGroupId();

    String getPrefix();

    String getSuffix();

    String getPlayerName();

    int getRank();

    String getTag();

    int getMultiplier();

    Map<String, Boolean> getPermissions();

    boolean hasPermission(String name);

    void refresh();


}
