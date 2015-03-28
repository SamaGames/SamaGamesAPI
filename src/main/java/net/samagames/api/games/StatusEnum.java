package net.samagames.api.games;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 *
 * Use this interface to create your own status enumeration, if you don't like the default one.
 */
public interface StatusEnum {
    /**
     * Internal status ID
     * @return a name which reprensents the status.
     */
    public String getId();

    /**
     * Determines if this status allows players to join, or if it doesn't.
     * @return a boolean (true : the player is allowed to join. false : the player cannot join). BEWARE : this boolean is checked on the lobby. It's not checked on the game server itself.
     */
    public boolean isAllowJoin();

    /**
     * @return the string displayed on the sign
     */
    public String getDisplay();
}
