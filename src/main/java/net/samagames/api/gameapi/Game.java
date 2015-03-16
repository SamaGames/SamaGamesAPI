package net.samagames.api.gameapi;

import net.samagames.api.network.JoinHandler;

/**
 * Created by vialarl on 09/03/2015.
 */
public interface Game extends JoinHandler {

	public int getMaxPlayers();
	public int getTotalMaxPlayers();
	public int getConnectedPlayers();
	public StatusEnum getStatus();
	public void displayMessage(String message);
}
