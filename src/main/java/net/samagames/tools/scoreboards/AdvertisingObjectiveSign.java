package net.samagames.tools.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvertisingObjectiveSign extends ObjectiveSign implements Runnable
{
    private final String originalDisplayName;

    private String advertisingBase;
    private int ticks;
    private int design;
    private boolean advertisingState;

    /*
        Relative to design 1
     */
    private int advertisingCursor;

    /*
        Relative to design 2
     */
    private int times;
    private boolean uppercase;

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

        this.advertisingBase = "mc.samagames.net";
        this.ticks = 0;
        this.design = 0;
        this.advertisingState = false;

        this.advertisingCursor = 0;

        this.times = 0;
        this.uppercase = false;

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 1L, 1L);
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

            this.ticks++;
        }
        else
        {
            if (this.design == 0 || this.design == 2)
            {
                String advertisingDisplayName = this.advertisingBase;

                if (this.advertisingCursor < this.advertisingBase.length())
                {
                    advertisingDisplayName = ChatColor.YELLOW + advertisingDisplayName.substring(0, this.advertisingCursor) + ChatColor.GOLD + Character.toUpperCase(advertisingDisplayName.charAt(this.advertisingCursor)) + ChatColor.YELLOW + advertisingDisplayName.substring(this.advertisingCursor + 1);
                    this.advertisingCursor++;
                }

                if (this.advertisingCursor == this.advertisingBase.length())
                {
                    advertisingDisplayName = ChatColor.YELLOW + advertisingDisplayName;
                    this.advertisingCursor = 0;

                    if (this.design == 0)
                    {
                        this.design++;
                    }
                    else
                    {
                        this.design = 0;
                        this.advertisingState = false;
                    }
                }

                this.setDisplayName(advertisingDisplayName);
            }
            else if (this.design == 1)
            {
                String advertisingDisplayName;

                if (this.uppercase)
                    advertisingDisplayName = ChatColor.GOLD + this.advertisingBase.toUpperCase();
                else
                    advertisingDisplayName = ChatColor.YELLOW + this.advertisingBase;

                this.uppercase = !this.uppercase;

                this.setDisplayName(advertisingDisplayName);

                if (this.times == 5)
                {
                    this.times = 0;
                    this.design++;
                }
            }
        }

        this.updateLines();
    }
}
