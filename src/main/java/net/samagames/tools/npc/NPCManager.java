package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.World;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager {

    public SamaGamesAPI api;

    private ScheduledExecutorService scheduler;

    private List<CustomNPC> entities = new ArrayList<>();

    protected List<OfflinePlayer> receivers = new ArrayList<>();

    private CallBack<CustomNPC> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;
        init();
    }

    public void init()
    {
        scheduler = Executors.newScheduledThreadPool(2);
    }

    public CustomNPC createNPC(Location location, UUID uuid)
    {
        final World w = ((CraftWorld) location.getWorld()).getHandle();
        final CustomNPC npc = new CustomNPC(w, new GameProfile(uuid, "[NPC]"+entities.size()));
        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entities.add(npc);

        if(scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        return npc;
    }

    public void disable()
    {
        scheduler.shutdown();
    }

    public CustomNPC getNPCEntity(String name)
    {
        for(CustomNPC entity : entities)
        {
            if(entity.getName().equals(name))
            {
                return entity;
            }
        }
        return null;
    }

    public void setScoreBoardRegister(CallBack<CustomNPC> scoreBoardRegister) {
        this.scoreBoardRegister = scoreBoardRegister;
    }
}
