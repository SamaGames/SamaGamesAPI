package net.samagames.tools.npc.nms;

import net.samagames.tools.holograms.Hologram;
import net.samagames.tools.npc.NPCInteractCallback;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 05/04/2017
 */
public interface ICustomNPC
{
    void onInteract(boolean rightClick, Player damager);

    void setLocation(Location location);
    Player getBukkitEntity();
    String getName();
    Object getWorld();

    ICustomNPC setCallback(NPCInteractCallback callback);

    void setHologram(Hologram hologram);
    Hologram getHologram();
}
