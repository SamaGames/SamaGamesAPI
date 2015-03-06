package net.samagames.samagamesapi.api.player;

import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface PlayerDataManager {

	public Map<String, String> getPlayerData(UUID player);
	public Map<String, String> getPlayerData(UUID player, boolean forceRefresh);
	public String getData(UUID player, String data);
	public void setData(UUID player, String data, String value);
	public void load(UUID player);
	public void unload(UUID player);

}
