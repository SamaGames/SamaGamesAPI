package net.samagames.tools.events;

public class Event
{
    private final String name;
    private final String description;
    private final String server;
    private final boolean currently;

    public Event(String name, String description, String server, boolean currently)
    {
        this.name = name;
        this.description = description;
        this.server = server;
        this.currently = currently;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getServer()
    {
        return this.server;
    }

    public boolean isCurrently()
    {
        return this.currently;
    }
}
