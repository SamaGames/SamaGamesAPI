package net.samagames.tools.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_9_R1.boss.CraftBossBar;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BossBarAPI
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class BossBarAPI
{
    private static final ConcurrentMap<UUID, CraftBossBar> bossBars = new ConcurrentHashMap<>();

    /**
     * Set a basic boss bar to all the players
     *
     * @param message Message on the top of the bar
     */
    public static void setBar(String message)
    {
        for (Player player : Bukkit.getOnlinePlayers())
            setBar(player, message);
    }

    /**
     * Set a boss bar with a given color, style and a progress to all the
     * players
     *
     * @param message Message on the top of the bar
     * @param color Color of the bar
     * @param style Style of the bar
     * @param progress Filling percentage
     */
    public static void setBar(String message, BarColor color, BarStyle style, double progress)
    {
        for (Player player : Bukkit.getOnlinePlayers())
            setBar(player, message, color, style, progress);
    }

    /**
     * Set a basic boss bar to a given player
     *
     * @param player Player
     * @param message Message on the top of the bar
     */
    public static void setBar(Player player, String message)
    {
        setBar(player, message, BarColor.PURPLE, BarStyle.SOLID, 100.0D);
    }

    /**
     * Set a boss bar with a given color, style and a progress to a
     * given player
     *
     * @param player Player
     * @param message Message on the top of the bar
     * @param color Color of the bar
     * @param style Style of the bar
     * @param progress Filling percentage
     */
    public static void setBar(Player player, String message, BarColor color, BarStyle style, double progress)
    {
        setBar(player, message, color, style, progress, false, false, false);
    }

    /**
     * Set a boss bar with a given color, style, percentage to a given
     * player. Also you can enable the darken sky, the boss music ambiance
     * and enable fog to a given player
     *
     * @param player Player
     * @param message Message on the top of the bar
     * @param color Color of the bar
     * @param style Style of the bar
     * @param progress Filling percentage
     * @param darkenSky Enable darken sky
     * @param playMusic Enable boss music
     * @param createFog Enable the fog
     */
    public static void setBar(Player player, String message, BarColor color, BarStyle style, double progress, boolean darkenSky, boolean playMusic, boolean createFog)
    {
        //if (bossBars.containsKey(player.getUniqueId()))
        //    removeBar(player);

        CraftBossBar bossBar = new CraftBossBar(message, color, style);
        bossBar.setProgress(progress / 100.0D);
        bossBar.addPlayer(player);

        if (darkenSky)
            bossBar.addFlag(BarFlag.DARKEN_SKY);

        if (playMusic)
            bossBar.addFlag(BarFlag.PLAY_BOSS_MUSIC);

        if (createFog)
            bossBar.addFlag(BarFlag.CREATE_FOG);

        //bossBars.put(player.getUniqueId(), bossBar);
    }

    /**
     * Remove a boss bar from a given player
     *
     * @param player Player
     */
    public static void removeBar(Player player)
    {
        if (bossBars.containsKey(player.getUniqueId()))
        {
            bossBars.get(player.getUniqueId()).removePlayer(player);
            bossBars.remove(player.getUniqueId());
        }
    }

    /**
     * Remove the boss bar of all the players
     */
    public static void flushBars()
    {
        bossBars.values().forEach(CraftBossBar::removeAll);
        bossBars.clear();
    }
}
