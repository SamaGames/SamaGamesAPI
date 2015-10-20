package net.samagames.tools.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Silva on 20/10/2015.
 */
public class NPCEntity {

    private Location position;

    private UUID uuid;
    private String name;

    private List<UUID> receivers = new ArrayList<>();

    private EntityPlayer entityPlayer;
    private GameProfile gameProfile;

    public NPCEntity(UUID uuid, String name, Location position)
    {

        this.uuid = uuid;

        if(name.length() > 16)
        {
            this.name = name.substring(0,16);
        }else{
            this.name = name;
        }

        this.position = position;

        gameProfile = new GameProfile(uuid, name);

        entityPlayer = generatePlayer();
    }

    protected EntityPlayer generatePlayer()
    {
        WorldServer world = ((CraftWorld) position.getWorld()).getHandle();
        PlayerInteractManager playerInteractManager = new PlayerInteractManager(world);
        EntityPlayer entityHuman = new EntityPlayer(world.getServer().getServer(), world, gameProfile, playerInteractManager);
        entityHuman.setLocation(position.getX(),
                position.getY(),
                position.getZ(),
                position.getYaw(),
                position.getPitch());
        return entityHuman;
    }

    public void spawnEntity(Player player)
    {
        sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        sendPacket(player, generateSpawnPacket(entityPlayer));
        sendPacket(player, new PacketPlayOutEntity.PacketPlayOutEntityLook(entityPlayer.getId(), (byte)entityPlayer.yaw, (byte)entityPlayer.pitch, true));
        //sendPacket(player, new PacketPlayOutEntityMetadata(entityPlayer.getId(), entityPlayer.getDataWatcher()));
        sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
    }

    public void destroyEntity(Player player)
    {
        sendPacket(player, new PacketPlayOutEntityDestroy(entityPlayer.getId()));
    }

    protected Packet generateSpawnPacket(EntityHuman entityHuman)
    {
        return new PacketPlayOutNamedEntitySpawn(entityHuman);
    }

    public EntityPlayer getEntityPlayer()
    {
        return entityPlayer;
    }

    public void setEntityPlayer(EntityPlayer entityPlayer)
    {
        this.entityPlayer = entityPlayer;
    }

    public Packet generateDestroyPacket(EntityHuman human)
    {
        return new PacketPlayOutEntityDestroy(human.getId());
    }

    public Location getPosition() {
        return position;
    }

    public void setPosition(Location position) {
        this.position = position;
        generatePlayer();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void addReceiver(UUID uuid)
    {
        receivers.add(uuid);
    }

    public void removeReceiver(UUID uuid)
    {
        receivers.remove(uuid);
    }

    public boolean isReceiver(UUID uuid)
    {
        return receivers.contains(uuid);
    }

    public List<UUID> getReceivers() {
        return receivers;
    }

    protected void sendPacket(Player p, Packet packet)
    {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
