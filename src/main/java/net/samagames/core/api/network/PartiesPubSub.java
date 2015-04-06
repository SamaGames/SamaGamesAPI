package net.samagames.core.api.network;

import net.md_5.bungee.api.chat.TextComponent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.channels.PacketsReceiver;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class PartiesPubSub implements PacketsReceiver {

	private final JoinManagerImplement implement;

	public PartiesPubSub(JoinManagerImplement implement) {
		this.implement = implement;
	}

	/*
	Protocol data :
	partyjoin <uuid of the party>
	 */

	@Override
	public void receive(String channel, String packet) {
		UUID partyID = UUID.fromString(packet);
		UUID leader = SamaGamesAPI.get().getPartiesManager().getLeader(partyID);
		Set<UUID> members = SamaGamesAPI.get().getPartiesManager().getPlayersInParty(partyID).keySet();
		members.remove(leader);

		JoinResponse response = new JoinResponse();
		for (JoinHandler handler : implement.handlerTreeMap.values())
			response = handler.onPreJoinParty(members, response);

		if (response.isAllowed()) {
			for (UUID player : members)
				SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(player).connect(SamaGamesAPI.get().getServerName());
		} else {
			TextComponent component = new TextComponent("Impossible de vous connecter : " + response.getReason());
			component.setColor(net.md_5.bungee.api.ChatColor.RED);
			SamaGamesAPI.get().getProxyDataManager().getProxiedPlayer(leader).sendMessage(component);
		}
	}
}
