package net.samagames.api.options;

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
public interface IServerOptions
{
    /**
     * Get if the tab we'll be filled with ranks
     *
     * @return {@code true} if activated
     */
    boolean hasRankTabColor();

    /**
     * Set tab filling with ranks
     *
     * @param enable {@code true} to enable
     */
    void setRankTabColorEnable(boolean enable);

    /**
     * Get if the basic minecraft nature is active or not.
     *
     * @return true {@code true} if activated
     */
    boolean hasActiveNature();

    /**
     * Set if the basic minecraft nature is active.
     *
     * @param enable {@code true} to enable
     */
    void setActiveNature(boolean enable);
}