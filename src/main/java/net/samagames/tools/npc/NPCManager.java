package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_9_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R1.PlayerInteractManager;
import net.minecraft.server.v1_9_R1.World;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.CallBack;
import net.samagames.tools.npc.nms.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCManager  implements Listener{

    public SamaGamesAPI api;

    private ScheduledExecutorService scheduler;

    private List<CustomNPC> entities = new ArrayList<>();

    private CallBack<CustomNPC> scoreBoardRegister;

    public NPCManager(SamaGamesAPI api)
    {
        this.api = api;
        scheduler = Executors.newScheduledThreadPool(2);

        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
    }

    public void onPlayerConnectionHook(Player p)
    {
        for(CustomNPC npc : entities)
        {
            updateNPC(p, npc);
        }
    }

    public void updateForAllNPC(CustomNPC npc)
    {
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        for(Player p : players)
        {
            updateNPC(p, npc);
        }
    }

    public void updateNPC(Player p, CustomNPC npc)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
    }

    public CustomNPC createNPC(Location location, UUID skinUUID)
    {
        final World w = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = createGameProfile(skinUUID, "[NPC]" + entities.size(), skinUUID);

        final CustomNPC npc = new CustomNPC(w, gameProfile, new PlayerInteractManager(w));
        npc.setLocation(location);
        npc.setCustomName("[NPC]" + entities.size());
        npc.setCustomNameVisible(true);
        w.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entities.add(npc);
        if(scoreBoardRegister != null)
            scoreBoardRegister.done(npc, null);

        Bukkit.getScheduler().runTaskLater(api.getPlugin(), () -> updateForAllNPC(npc), 2L);
        return npc;
    }

    public void removeNPC(String name)
    {
        CustomNPC npc = getNPCEntity(name);
        if(npc != null)
            npc.getWorld().removeEntity(npc);

    }

    public GameProfile createGameProfile(UUID uuid, String name, UUID skinURL)
    {
        GameProfile gameProfile = new GameProfile(uuid, name);
        /*String key = "gameprofiles." + skinURL.toString();

        try {


            String cache = null;

            Jedis jedis = api.getBungeeResource();
            if (jedis != null)
            {
                if(jedis.exists(key))
                {
                    cache = jedis.get(key);
                }else{
                    cache = Net.executeGet("https://sessionserver.mojang.com/session/minecraft/profile/" + skinURL.toString().replaceAll("-", "") + "?unsigned=false");
                }
                jedis.close();
            }

            JsonObject jsonObject = new JsonParser().parse(cache).getAsJsonObject();
            JsonArray properties = jsonObject.getAsJsonArray("properties");
            for(JsonElement property : properties)
            {
                JsonObject oProp = property.getAsJsonObject();

                gameProfile.getProperties().put(oProp.get("name").getAsString(),
                        new Property(oProp.get("name").getAsString(),
                                oProp.get("value").getAsString(),
                                oProp.get("signature").getAsString()
                        )
                );
            }

            jedis = api.getBungeeResource();
            jedis.set(key, cache);
            jedis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Jedis jedis = api.getBungeeResource();
            jedis.del(key);
            jedis.close();
        }*/
        return gameProfile;
    }

    public void getSkin(UUID uuid)
    {
        /*try {
            String result = Net.executeGet("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", ""));



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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

    @EventHandler
    public void onPlayerHitNPC(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof CustomNPC && event.getDamager() instanceof Player)
        {
            CustomNPC npc = (CustomNPC) event.getEntity();
            npc.onInteract(false, (Player) event.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteractNPC(PlayerInteractEntityEvent event)
    {
        if(event.getRightClicked() instanceof CustomNPC)
        {
            CustomNPC npc = (CustomNPC) event.getRightClicked();
            npc.onInteract(true, event.getPlayer());
        }
    }
}
