package net.samagames.tools.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

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
