package net.samagames.tools.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvertisingObjectiveSign extends ObjectiveSign implements Runnable
{
    private final String originalDisplayName;
    private final String advertisingText;
    private int ticks;
    private int advertisingCursor;
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
        this.advertisingText = "Vous jouez sur mc.samagames.net !";

        this.ticks = 0;
        this.advertisingCursor = 0;
        this.advertisingState = false;


        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 2L, 2L);
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

            this.ticks += 2;
        }
        else
        {
            this.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + this.advertisingText.substring(this.advertisingCursor, this.advertisingCursor + 10));

            this.advertisingCursor++;

            if (this.advertisingCursor >= this.advertisingText.length() - 10)
            {
                this.advertisingCursor = 0;
                this.advertisingState = false;
            }
        }

        this.updateLines();
    }
}
