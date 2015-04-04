package net.samagames.core.api.network;

import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.api.network.ProxiedPlayer;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ProxiedPlayerNoDB implements ProxiedPlayer {
	@Override
	public String getServer() {
		return "";
	}

	@Override
	public String getProxy() {
		return "";
	}

	@Override
	public void disconnect(TextComponent reason) {

	}

	@Override
	public void connect(String server) {

	}

	@Override
	public void sendMessage(TextComponent component) {

	}
}
