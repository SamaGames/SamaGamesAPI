package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.bossbar.BossBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class AdvertisingTask extends BukkitRunnable
{
    private final BossBar advertisingBossBar;
    private int loop;

    AdvertisingTask()
    {
        this.advertisingBossBar = BossBarAPI.getBar(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !", BarColor.RED, BarStyle.SOLID, 100.0D).getValue();
        this.loop = 0;

        this.runTaskTimer(SamaGamesAPI.get().getPlugin(), 10L, 10L);
    }

    @Override
    public void run()
    {
        if (this.loop < 10)
            this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
        else if (this.loop < 11)
            this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !");
        else if (this.loop < 12)
            this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
        else if (this.loop < 13)
            this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !");
        else if (this.loop < 14)
            this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
        else if (this.loop < 15)
            this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !");

        this.loop++;

        if (this.loop >= 15)
            this.loop = 0;
    }

    public void addPlayer(Player player)
    {
        this.advertisingBossBar.addPlayer(player);
    }

    public void removePlayer(Player player)
    {
        this.advertisingBossBar.removePlayer(player);
    }
}
