package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.bossbar.BossBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
class AdvertisingTask extends BukkitRunnable
{
    private final BossBar bossBar;
    private String lastMessage;
    private int style;
    private int loop;

    AdvertisingTask()
    {
        this.bossBar = BossBarAPI.getBar("").getValue();
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
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !";
            else if (this.loop < 22)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !";
            else if (this.loop < 24)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !";
            else if (this.loop < 26)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !";
            else if (this.loop < 28)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !";
            else if (this.loop < 30)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.RED + "mc.samagames.net" + ChatColor.YELLOW + " !";
        }
        else if (this.style == 1)
        {
            if (this.loop < 20)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + "mc.samagames.net" + ChatColor.YELLOW + " !";
            else if (this.loop < 36)
                this.lastMessage = ChatColor.YELLOW + "Vous jouez sur " + ChatColor.GOLD + this.colorIpAt() + ChatColor.YELLOW + " !";
        }

        this.bossBar.setTitle(this.lastMessage);

        this.loop++;

        if ((this.style == 0 && this.loop >= 30) || (this.style == 1 && this.loop >= 36))
        {
            this.loop = 0;
            this.style++;

            if (this.style >= 2)
                this.style = 0;
        }
    }

    public void addPlayer(Player player)
    {
        this.bossBar.addPlayer(player);
    }

    public void removePlayer(Player player)
    {
        this.bossBar.removePlayer(player);
    }

    private String colorIpAt()
    {
        int charIndex = this.loop - 20;
        String ip = "mc.samagames.net";

        StringBuilder formattedIp = new StringBuilder();

        if (charIndex > 0)
        {
            formattedIp.append(ip.substring(0, charIndex - 1));
            formattedIp.append(ChatColor.YELLOW).append(ip.substring(charIndex - 1, charIndex));
        }
        else
        {
            formattedIp.append(ip.substring(0, charIndex));
        }

        formattedIp.append(ChatColor.RED).append(ip.charAt(charIndex));

        if (charIndex + 1 < ip.length())
        {
            formattedIp.append(ChatColor.YELLOW).append(ip.charAt(charIndex + 1));

            if (charIndex + 2 < ip.length())
                formattedIp.append(ChatColor.GOLD).append(ip.substring(charIndex + 2));
        }

        return formattedIp.toString();
    }
}
