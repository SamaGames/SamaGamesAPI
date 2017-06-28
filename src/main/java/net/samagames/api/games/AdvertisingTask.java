package net.samagames.api.games;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class AdvertisingTask extends BukkitRunnable
{
    private String lastMessage;
    private int style;
    private int loop;

    AdvertisingTask()
    {
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

        BarAPI.setMessage(this.lastMessage);

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
        BarAPI.setMessage(player, this.lastMessage);
    }

    public void removePlayer(Player player)
    {
        BarAPI.removeBar(player);
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
