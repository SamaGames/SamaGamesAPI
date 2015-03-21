package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import net.samagames.hub.common.Event;

public class EventUtils
{
    public static void setEvent(Event event)
    {
        SamaGamesAPI.get().getResource().set("event:name", event.getName());
        SamaGamesAPI.get().getResource().set("event:description", event.getDescription());
        SamaGamesAPI.get().getResource().set("event:server", event.getServer());
        SamaGamesAPI.get().getResource().set("event:currently", String.valueOf(event.isCurrently()));
    }

    public static Event getCurrentEvent()
    {
        if(!isCurrentlyEvent())
            return null;

        String name = SamaGamesAPI.get().getResource().get("event:name");
        String description = SamaGamesAPI.get().getResource().get("event:description");
        String server = SamaGamesAPI.get().getResource().get("event:server");

        return new Event(name, description, server, true);
    }

    public static boolean isCurrentlyEvent()
    {
        if(!SamaGamesAPI.get().getResource().exists("event:currently"))
            return false;

        return Boolean.valueOf(SamaGamesAPI.get().getResource().get("event:currently"));
    }
}
