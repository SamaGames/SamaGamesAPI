package net.samagames.core.api.resourcepacks;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.samagames.api.protocol.TinyProtocol;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ProtocolHandler extends TinyProtocol {
	private final ResourcePacksManagerImpl manager;

	/**
	 * Construct a new instance of TinyProtocol, and start intercepting packets for all connected clients and future clients.
	 * <p>
	 * You can construct multiple instances per plugin.
	 *
	 * @param plugin - the plugin.
	 */
	public ProtocolHandler(Plugin plugin, ResourcePacksManagerImpl manager) {
		super(plugin);
		this.manager = manager;
	}

	@Override
	public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
		if(sender == null)
			return super.onPacketInAsync(sender, channel, packet);

		if (packet instanceof PacketPlayInResourcePackStatus) {
			PacketPlayInResourcePackStatus status = (PacketPlayInResourcePackStatus) packet;
			try {
				Field hashField = status.getClass().getDeclaredField("a");
				hashField.setAccessible(true);
				Field stateField = status.getClass().getDeclaredField("b");
				stateField.setAccessible(true);

				String hash = (String) hashField.get(status);
				PacketPlayInResourcePackStatus.EnumResourcePackStatus state = (PacketPlayInResourcePackStatus.EnumResourcePackStatus) stateField.get(status);

				manager.handle(sender, hash, state);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return super.onPacketInAsync(sender, channel, packet);
	}
}
