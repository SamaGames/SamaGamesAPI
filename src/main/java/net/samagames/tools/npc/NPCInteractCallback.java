package net.samagames.tools.npc;

/**
 * Created by Silva on 18/12/2015.
 */

import org.bukkit.entity.Player;

/**
 * CallBack class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface NPCInteractCallback
{
    /**
     * Function called after action
     *
     * @param rightClick true if right clicked or false left clicked
     * @param player player who performed the action
     */
    void done(boolean rightClick, Player player);
}
