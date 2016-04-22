package net.samagames.api.permissions;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Silvanosky on 14/03/2016.
 */
public interface IPermissionsEntity {

    UUID getUUID();

    long getGroupId();

    String getDisplayPrefix();

    String getPrefix();

    String getDisplaySuffix();

    String getSuffix();

    int getDisplayRank();

    int getRank();

    String getDisplayTag();

    String getTag();

    int getMultiplier();

    Map<String, Boolean> getPermissions();

    boolean hasPermission(String name);

    void refresh();


}
