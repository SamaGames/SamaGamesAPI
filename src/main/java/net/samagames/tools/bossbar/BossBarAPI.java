package net.samagames.tools.bossbar;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
public class BossBarAPI
{
    private static final ConcurrentMap<UUID, BossBar> bossBars = new ConcurrentHashMap<>();

    /**
     * Set a basic boss bar to all the players
     *
     * @param message Message on the top of the bar
     */
    public static Pair<UUID, BossBar> getBar(String message)
    {
        return getBar(message, BarColor.PURPLE, BarStyle.SOLID, 100.0D);
    }

    /**
     * Set a boss bar with a given color, style and a progress to a
     * given player
     *
     * @param message Message on the top of the bar
     * @param color Color of the bar
     * @param style Style of the bar
     * @param progress Filling percentage
     */
    public static Pair<UUID, BossBar> getBar(String message, BarColor color, BarStyle style, double progress)
    {
        return getBar(message, color, style, progress, false, false, false);
    }

    /**
     * Set a boss bar with a given color, style, percentage to a given
     * player. Also you can enable the darken sky, the boss music ambiance
     * and enable fog to a given player
     *
     * @param message Message on the top of the bar
     * @param color Color of the bar
     * @param style Style of the bar
     * @param progress Filling percentage
     * @param darkenSky Enable darken sky
     * @param playMusic Enable boss music
     * @param createFog Enable the fog
     */
    public static Pair<UUID, BossBar> getBar(String message, BarColor color, BarStyle style, double progress, boolean darkenSky, boolean playMusic, boolean createFog)
    {
        BossBar bossBar = Bukkit.createBossBar(message, color, style);
        bossBar.setProgress(progress < 0 ? 0D : progress / 100.0D);

        if (darkenSky)
            bossBar.addFlag(BarFlag.DARKEN_SKY);

        if (playMusic)
            bossBar.addFlag(BarFlag.PLAY_BOSS_MUSIC);

        if (createFog)
            bossBar.addFlag(BarFlag.CREATE_FOG);

        UUID random = UUID.randomUUID();
        bossBars.put(random, bossBar);

        return Pair.of(random, bossBar);
    }

    /**
     * Remove a boss bar from a given player
     *
     * @param player Player
     */
    public static void removeBar(Player player)
    {
        Map<UUID, BossBar> list = new HashMap<>(bossBars);
        list.forEach((uuid, bossBar) ->
        {
            bossBar.removePlayer(player);
            if (bossBar.getPlayers().isEmpty())
                bossBars.remove(uuid);
        });
    }

    /**
     * Remove the boss bar of all the players
     */
    public static void flushBars()
    {
        bossBars.values().forEach(BossBar::removeAll);
        bossBars.clear();
    }
}