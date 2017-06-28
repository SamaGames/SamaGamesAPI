package net.samagames.tools.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvertisingObjectiveSign extends ObjectiveSign implements Runnable
{
    private final String originalDisplayName;
    private final String advertisingText;

    private String finalText;
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
        this.advertisingText = "      Vous jouez sur mc.samagames.net !                  "; // 6 spaces before and 18 spaces after

        this.finalText = this.advertisingText;
        this.ticks = 0;
        this.advertisingCursor = 0;
        this.advertisingState = false;

        plugin.getServer().getScheduler().runTaskTimer(plugin, this, 5L, 5L);
    }

    @Override
    public void updateLines()
    {
        this.setDisplayName(this.finalText);
        super.updateLines();
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
                this.finalText = this.originalDisplayName;
            }
            else if (this.ticks == 150)
            {
                this.advertisingState = true;
                this.ticks = 0;

                return;
            }

            this.ticks += 5;
        }
        else
        {
            this.finalText = ChatColor.YELLOW + "" + ChatColor.BOLD + this.advertisingText.substring(this.advertisingCursor, this.advertisingCursor + 16);

            this.advertisingCursor++;

            if (this.advertisingCursor >= this.advertisingText.length() - 16)
            {
                this.advertisingCursor = 0;
                this.advertisingState = false;
            }
        }

        this.updateLines();
    }
}
