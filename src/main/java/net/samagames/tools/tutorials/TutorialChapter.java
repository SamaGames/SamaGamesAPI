package net.samagames.tools.tutorials;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


/**
 * Represents a chapter of an in-game tutorial.
 * <p/>
 * A chapter contains a title, displayed the whole time above, and one or more
 * “pages”, displayed as subtitle of the {@code /title} command, ordered.
 *
 * @author Amaury Carrade
 */
public class TutorialChapter
{

    private Location location;

    private String title;
    private List<Pair<String, Long>> content;

    private boolean displayInChat = true;


    /**
     * @param location The watching point of the chapter.
     * @param title    The title of this chapter.
     * @param content  The content of this chapter.
     */
    public TutorialChapter(Location location, String title, List<Pair<String, Long>> content)
    {
        this.location = location;
        this.title = title;
        this.content = content;
    }

    /**
     * @param location The watching point of the chapter.
     * @param title    The title of this chapter.
     * @param content  The content of this chapter.
     */
    public TutorialChapter(Location location, String title, List<Pair<String, Long>> content, boolean displayInChat)
    {
        this(location, title, content);

        this.displayInChat = displayInChat;
    }


    /**
     * Teleports the given player to the watching point of this chapter.
     *
     * @param uuid The UUID of the player.
     */
    public void teleport(UUID uuid)
    {
        teleport(Bukkit.getPlayer(uuid));
    }

    /**
     * Teleports the given player to the watching point of this chapter.
     *
     * @param player The player.
     */
    public void teleport(Player player)
    {
        player.teleport(location);
    }

    /**
     * Returns the title of this chapter.
     *
     * @return The title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the content of this chapter (a list of strings).
     *
     * @return The content.
     */
    public List<Pair<String, Long>> getContent()
    {
        return content;
    }

    /**
     * Returns true if this has to be displayed in the chat.
     *
     * @return {@code true} if displayed in the chat.
     */
    public boolean isDisplayedInChat()
    {
        return displayInChat;
    }
}
