package net.samagames.core;

import net.samagames.api.channels.PacketsReceiver;
import org.bukkit.Bukkit;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class RemoteCommandsHandler implements PacketsReceiver {
	@Override
	public void receive(String channel, String command) {
		Bukkit.getLogger().info("Executing command remotely : " + command);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
}
