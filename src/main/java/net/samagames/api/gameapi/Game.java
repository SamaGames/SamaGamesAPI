package net.samagames.api.gameapi;

import net.samagames.api.network.JoinHandler;

public interface Game extends JoinHandler {

	public int getMaxPlayers();
	public int getTotalMaxPlayers();
	public int getConnectedPlayers();
	public StatusEnum getStatus();
	public void displayMessage(String message);
	public String getMapName();
}
