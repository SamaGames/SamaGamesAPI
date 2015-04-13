package net.samagames.core.listeners;

import net.samagames.tools.TinyProtocol;
import org.bukkit.plugin.Plugin;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by Geekpower14 on 13/04/2015.
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */
public class NicknamePacketListener extends TinyProtocol{


    /**
     * Construct a new instance of TinyProtocol, and start intercepting packets for all connected clients and future clients.
     * <p/>
     * You can construct multiple instances per plugin.
     *
     * @param plugin - the plugin.
     */
    public NicknamePacketListener(Plugin plugin) {
        super(plugin);
    }


}
