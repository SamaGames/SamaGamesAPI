package net.samagames.core.listeners;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R2.DataWatcher;
import net.minecraft.server.v1_8_R2.PacketPlayOutNamedEntitySpawn;
import net.samagames.tools.TinyProtocol;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.UUID;

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

    @Override
    public Object onPacketOutAsync(Player reciever, Channel channel, Object packet) {

        if(packet instanceof PacketPlayOutNamedEntitySpawn)
        {
            PacketPlayOutNamedEntitySpawn p = (PacketPlayOutNamedEntitySpawn)packet;

            try {
                Field uuid = p.getClass().getDeclaredField("b");
                uuid.setAccessible(true);
                uuid.set(p, UUID.randomUUID());
                uuid.setAccessible(false);

                Field dataWatcher = p.getClass().getDeclaredField("i");
                dataWatcher.setAccessible(true);
                DataWatcher dWtacher = (DataWatcher) dataWatcher.get(p);

                dWtacher.a(4, "");
                dataWatcher.set(p, dWtacher);
                dataWatcher.setAccessible(false);


            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            packet = p;
        }

        return super.onPacketOutAsync(reciever, channel, packet);
    }




}
