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
    private int style;
    private int loop;

    AdvertisingTask()
    {
        this.advertisingBossBar = BossBarAPI.getBar(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !", BarColor.RED, BarStyle.SOLID, 100.0D).getValue();
        this.style = 0;
        this.loop = 0;

        this.runTaskTimer(SamaGamesAPI.get().getPlugin(), 5L, 5L);
    }

    @Override
    public void run()
    {
        if (this.style == 0)
        {
            if (this.loop < 20)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 22)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 24)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 26)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 28)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 30)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !");
        }
        else if (this.style == 1)
        {
            if (this.loop < 20)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 34)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + this.upIpAt(this.loop) + ChatColor.YELLOW + " !");
        }
        else if (this.style == 2)
        {
            if (this.loop < 20)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !");
            else if (this.loop < 34)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + this.colorIpAt(ChatColor.RED, this.loop) + ChatColor.YELLOW + " !");
            else if (this.loop < 48)
                this.advertisingBossBar.setTitle(ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + this.colorIpAt(ChatColor.GOLD, this.loop) + ChatColor.YELLOW + " !");
        }

        this.loop++;

        if (this.loop >= 15)
        {
            this.loop = 0;
            this.style++;

            if (this.style >= 3)
                this.style = 0;
        }
    }

    public void addPlayer(Player player)
    {
        this.advertisingBossBar.addPlayer(player);
    }

    public void removePlayer(Player player)
    {
        this.advertisingBossBar.removePlayer(player);
    }

    private String upIpAt(int loop)
    {
        int charIndex = loop - 20;
        String ip = "mc.samagames.net";

        return ip.substring(0, charIndex) + Character.toUpperCase(ip.charAt(charIndex)) + ip.substring(charIndex + 1);
    }

    private String colorIpAt(ChatColor color, int loop)
    {
        int charIndex = loop - 20;
        String ip = "mc.samagames.net";

        return ip.substring(0, charIndex) + color + ip.charAt(charIndex) + ChatColor.GOLD + ip.substring(charIndex + 1);
    }
}
