package net.samagames.api.games;

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
public enum GamesNames {
    GLOBAL(0),
    HEROBATTLE(1),
    JUKEBOX(2),
    QUAKE(3),
    UHCRUN(4),
    UPPERVOID(5),
    DIMENSION(6),
    BOWLING(7),
    UHCORIGINAL(8),
    DOUBLERUNNER(9),
    UHCRANDOM(10),
    RANDOMRUN(11),
    ULTRAFLAGKEEPER(12),
    CHUNKWARS(13),
    THEDROPPER(14);

    private int value;
    GamesNames(int value)
    {
        this.value = value;
    }

    public int intValue()
    {
        return value;
    }
}
