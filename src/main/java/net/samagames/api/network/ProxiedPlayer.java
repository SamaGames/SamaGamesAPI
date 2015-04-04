package net.samagames.api.network;

import net.md_5.bungee.api.chat.TextComponent;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ProxiedPlayer {

	public String getServer();
	public String getProxy();
	public void disconnect(TextComponent reason);
	public void connect(String server);

	public void sendMessage(TextComponent component);

}
