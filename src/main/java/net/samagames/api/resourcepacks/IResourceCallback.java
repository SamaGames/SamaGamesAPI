package net.samagames.api.resourcepacks;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public interface IResourceCallback
{
    /**
     * Called when the download status is changed
     * WARNING DON'T KICK THE PLAYER HERE
     *
     * @param player The player downloading the pack
     * @param status The download status
     */
    void callback(Player player, PlayerResourcePackStatusEvent.Status status);

    /**
     * Called when a player is about to be automatically kicked because he doesn't have the pack
     *
     * @param player The player to be kicked
     *
     * @return {@code true} to kick the player, false to cancel
     */
    boolean automaticKick(Player player);
}
