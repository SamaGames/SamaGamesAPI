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
 * NPC entity class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class NPCEntity
{
    private Location position;

    private UUID uuid;
    private String name;

    private List<UUID> receivers = new ArrayList<>();

    private EntityPlayer entityPlayer;
    private GameProfile gameProfile;

    /**
     * Constructor
     *
     * @param uuid NPC UUID
     * @param name NPC name
     * @param position NPC location
     */
    public NPCEntity(UUID uuid, String name, Location position)
    {
        this.uuid = uuid;

        if(name.length() > 16)
            this.name = name.substring(0, 16);
        else
            this.name = name;

        this.position = position;
        this.gameProfile = new GameProfile(uuid, name);
        this.entityPlayer = generatePlayer();
    }

    /**
     * Add receiver
     *
     * @param uuid Player's UUID
     */
    public void addReceiver(UUID uuid)
    {
        this.receivers.add(uuid);
    }

    /**
     * Remove receiver
     *
     * @param uuid Player's UUID
     */
    public void removeReceiver(UUID uuid)
    {
        this.receivers.remove(uuid);
    }

    /**
     * Send the NPC to the given player
     *
     * @param player Player
     */
    public void spawnEntity(Player player)
    {
        this.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        this.sendPacket(player, this.generateSpawnPacket(this.entityPlayer));
        this.sendPacket(player, new PacketPlayOutEntity.PacketPlayOutEntityLook(this.entityPlayer.getId(), (byte) this.entityPlayer.yaw, (byte) this.entityPlayer.pitch, true));
        this.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entityPlayer));
    }

    /**
     * Remove the NPC to the given player
     *
     * @param player Player
     */
    public void destroyEntity(Player player)
    {
        this.sendPacket(player, new PacketPlayOutEntityDestroy(this.entityPlayer.getId()));
    }

    /**
     * Get NPC entity
     *
     * @return Entity
     */
    public EntityPlayer getEntityPlayer()
    {
        return this.entityPlayer;
    }

    /**
     * Get NPC location
     *
     * @return Location
     */
    public Location getPosition()
    {
        return this.position;
    }

    /**
     * Get NPC UUID
     *
     * @return UUID
     */
    public UUID getUUID()
    {
        return this.uuid;
    }

    /**
     * Get NPC name
     *
     * @return Name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Get NPC receivers
     *
     * @return List of UUID
     */
    public List<UUID> getReceivers()
    {
        return this.receivers;
    }

    /**
     * Is given player's UUID receiving the NPC
     *
     * @param uuid Player's UUID
     *
     * @return {@code true} if receiving
     */
    public boolean isReceiver(UUID uuid)
    {
        return this.receivers.contains(uuid);
    }

    /**
     * Create the packet to spawn the NPC
     *
     * @param entityHuman NPC
     *
     * @return Packet instance
     */
    private Packet generateSpawnPacket(EntityHuman entityHuman)
    {
        return new PacketPlayOutNamedEntitySpawn(entityHuman);
    }

    /**
     * Create the packet to destroy the NPC
     *
     * @param entityHuman NPC
     *
     * @return Packet instance
     */
    private Packet generateDestroyPacket(EntityHuman entityHuman)
    {
        return new PacketPlayOutEntityDestroy(entityHuman.getId());
    }

    /**
     * Create a EntityPlayer {@link EntityPlayer}
     *
     * @return Entity instance
     */
    private EntityPlayer generatePlayer()
    {
        WorldServer world = ((CraftWorld) this.position.getWorld()).getHandle();
        PlayerInteractManager playerInteractManager = new PlayerInteractManager(world);

        EntityPlayer entityHuman = new EntityPlayer(world.getServer().getServer(), world, this.gameProfile, playerInteractManager);
        entityHuman.setLocation(position.getX(), this.position.getY(), this.position.getZ(), this.position.getYaw(), this.position.getPitch());

        return entityHuman;
    }

    /**
     * Send a given packet to a given player
     *
     * @param p Player
     * @param packet Packet
     */
    private void sendPacket(Player p, Packet packet)
    {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
