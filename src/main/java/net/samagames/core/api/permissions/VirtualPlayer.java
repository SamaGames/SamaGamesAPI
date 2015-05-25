package net.samagames.core.api.permissions;

import net.samagames.core.APIPlugin;
import net.samagames.permissionsapi.rawtypes.RawPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class VirtualPlayer implements RawPlayer {

    protected Player player;
    protected UUID id;
    protected PermissionAttachment attachment;

    public VirtualPlayer(Player p) {
        this.player = p;
        this.id = p.getUniqueId();
        this.attachment = player.addAttachment(APIPlugin.getInstance());
    }

    @Override
    public void setPermission(String permission, boolean value) {
        if (player != null) {
            attachment.setPermission(permission, value);
        }
    }

    @Override
    public UUID getUniqueId() {
        return id;
    }

    @Override
    public void clearPermissions() {
        ArrayList<String> perms = new ArrayList<>();
        for (String perm : attachment.getPermissions().keySet())
            perms.add(perm);

        perms.forEach(attachment::unsetPermission);
    }
}
