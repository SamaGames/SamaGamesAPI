package net.samagames.tools.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvertisingObjectiveSign extends ObjectiveSign implements Runnable
{
    private final String originalDisplayName;
    private int ticks;
    private int designStep;
    private boolean advertisingState;

    /**
     * Constructor
     *
     * @param plugin      Plugin
     * @param name        Objective's name
     * @param displayName Objective's display name
     */
    public AdvertisingObjectiveSign(JavaPlugin plugin, String name, String displayName)
    {
        super(name, displayName);

        this.originalDisplayName = displayName;

        this.ticks = 0;
        this.designStep = 0;
        this.advertisingState = false;


        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 10L, 10L);
    }

    /**
     * Create and apply the design of the scoreboard's title
     */
    @Override
    public void run()
    {
        if (!this.advertisingState)
        {
            if (this.ticks == 0)
            {
                this.setDisplayName(this.originalDisplayName);
            }
            else if (this.ticks == 100)
            {
                this.advertisingState = true;
                this.ticks = 0;

                return;
            }

            this.ticks += 10;
        }
        else
        {
            if (this.designStep == 0)
                this.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Vous");
            else if (this.designStep == 1)
                this.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "jouez");
            else if (this.designStep == 2)
                this.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "sur");
            else if (this.designStep == 3)
                this.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "mc.samagames.net");
            else if (this.designStep == 4)
                this.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "mc.samagames.net");
            else if (this.designStep == 5)
                this.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "mc.samagames.net");
            else if (this.designStep == 6)
                this.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "mc.samagames.net");
            else if (this.designStep == 7)
                this.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "mc.samagames.net");

            this.designStep++;

            if (this.designStep == 8)
            {
                this.advertisingState = false;
                this.designStep = 0;
            }
        }

        this.updateLines();
    }
}
