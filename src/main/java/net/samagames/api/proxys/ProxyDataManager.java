package net.samagames.api.proxys;

import java.util.Map;
import java.util.UUID;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface ProxyDataManager {

	public Map<String, String> getPlayerData(UUID player);

	public String getPlayerServer(UUID player);
	public String getPlayerProxy(UUID player);
	public String getPlayerIP(UUID player);

}
