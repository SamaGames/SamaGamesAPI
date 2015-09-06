package net.samagames.tools.tutorials;

import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.Titles;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;


public class TutorialRunner implements Runnable
{
    private Plugin p;

    private final String tutorialInChatPrefix = ChatColor.GRAY + "│ " + ChatColor.RESET;

    private Player player;
    private Tutorial tutorial;

    private int currentChapter = 0;
    private int currentText = 0;


    public TutorialRunner(Tutorial tutorial, UUID playerId)
    {
	    this.p = SamaGamesAPI.get().getPlugin();

	    this.player   = p.getServer().getPlayer(playerId);
        this.tutorial = tutorial;
    }


    @Override
    public void run()
    {
        if (!player.isOnline())
        {
            tutorial.stop(player.getUniqueId(), true);
            return;
        }

        if (currentChapter == tutorial.getContent().size()) // The end.
        {
            tutorial.stop(player.getUniqueId(), false);
            return;
        }


        TutorialChapter chapter = tutorial.getContent().get(currentChapter);

        // Delays of fade-in, fade-out and display
        int fadeIn = (currentText == 0) ? 10 : 0;
        int fadeOut = (currentText == chapter.getContent().size() - 1) ? 10 : 0;
        int readingTime = (int) (fadeOut == 10 ? tutorial.getReadingTime() - 10 : tutorial.getReadingTime() + 5);


        // New chapter, new location
        if (currentText == 0)
        {
            chapter.teleport(player);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1L, 2L);
        }


        // Title version
        Titles.sendTitle(
                player,
                fadeIn, readingTime, fadeOut,
                chapter.getTitle(),
                chapter.getContent().get(currentText)
        );


        // Chat version
        if (chapter.isDisplayedInChat())
        {
            if (currentText == 0) player.sendMessage(tutorialInChatPrefix + chapter.getTitle());
            player.sendMessage(tutorialInChatPrefix + chapter.getContent().get(currentText));
        }


        // Next one?
        currentText++;
        if (currentText == chapter.getContent().size())
        {
            currentChapter++;
            currentText = 0;
        }
    }
}
