package net.samagames.tools;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Rainbow
{
    public static ArrayList<ChatColor> getRainbow()
    {
        ArrayList<ChatColor> rainbowContent = new ArrayList<>();

        rainbowContent.add(ChatColor.DARK_RED);
        rainbowContent.add(ChatColor.RED);
        rainbowContent.add(ChatColor.GOLD);
        rainbowContent.add(ChatColor.YELLOW);
        rainbowContent.add(ChatColor.GREEN);
        rainbowContent.add(ChatColor.DARK_AQUA);
        rainbowContent.add(ChatColor.DARK_BLUE);
        rainbowContent.add(ChatColor.DARK_AQUA);
        rainbowContent.add(ChatColor.GREEN);
        rainbowContent.add(ChatColor.YELLOW);
        rainbowContent.add(ChatColor.GOLD);
        rainbowContent.add(ChatColor.RED);
        rainbowContent.add(ChatColor.DARK_RED);

        return rainbowContent;
    }
}
