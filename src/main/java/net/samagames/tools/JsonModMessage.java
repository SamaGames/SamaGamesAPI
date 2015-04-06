package net.samagames.tools;

import net.md_5.bungee.api.ChatColor;
import net.samagames.api.SamaGamesAPI;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.command.CommandSender;
import com.google.gson.Gson;
import org.bukkit.entity.Player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class JsonModMessage {

	protected String sender;
	protected ChatColor senderPrefix;
	protected String message;

	public JsonModMessage() {
	}

	public JsonModMessage(String sender, ChatColor senderPrefix, String message) {
		this.sender = sender;
		this.senderPrefix = senderPrefix;
		this.message = message;
	}

	public static JsonModMessage build(CommandSender sender, String message) {
		if (sender instanceof Player) {
			String prefix = PermissionsBukkit.getPrefix(PermissionsBukkit.getApi().getUser(((Player)sender).getUniqueId()));
			ChatColor pr = (prefix == null) ? ChatColor.AQUA : ChatColor.getByChar(prefix.charAt(prefix.length() - 1));

			return new JsonModMessage(sender.getName(), pr, message);
		} else {
			return new JsonModMessage(sender.getName(), ChatColor.AQUA, message);
		}
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public ChatColor getSenderPrefix() {
		return senderPrefix;
	}

	public void setSenderPrefix(ChatColor senderPrefix) {
		this.senderPrefix = senderPrefix;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void send() {
		SamaGamesAPI.get().getPubSub().send("moderationchan", new Gson().toJson(this));
	}
}
