package net.samagames.tools;

import net.minecraft.server.v1_8_R3.*;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by Geekpower14 on 14/03/2015.
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */
public class Hologram {
    private static final double distance = 0.23;
    private List<String> lines = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    private boolean showing = false;
    private Location location;

    public Hologram(String... lines) {
        this.lines.addAll(Arrays.asList(lines));
    }

    private static List<Integer> showLine(Location loc, String text) {
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand skull = new EntityArmorStand(world);
        skull.setLocation(loc.getX(), loc.getY() + 1 + 55, loc.getZ(), 0, 0);
        PacketPlayOutSpawnEntity skull_packet = new PacketPlayOutSpawnEntity(skull, 78);

        EntityHorse horse = new EntityHorse(world);
        horse.setLocation(loc.getX(), loc.getY() + 55, loc.getZ(), 0, 0);
        horse.setAge(-1700000);
        horse.setCustomName(text);
        horse.setCustomNameVisible(true);
        PacketPlayOutSpawnEntityLiving packedt = new PacketPlayOutSpawnEntityLiving(horse);
        for (Player player : loc.getWorld().getPlayers()) {
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.playerConnection.sendPacket(packedt);
            nmsPlayer.playerConnection.sendPacket(skull_packet);

            PacketPlayOutAttachEntity pa = new PacketPlayOutAttachEntity(0, horse, skull);
            nmsPlayer.playerConnection.sendPacket(pa);
        }
        return Arrays.asList(skull.getId(), horse.getId());
    }

    public void change(String... lines) {
        destroy();
        this.lines = Arrays.asList(lines);
        show(this.location);
    }

    public void show(Location loc) {
        if (showing == true) {
            try {
                throw new Exception("Is already showing!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Location first = loc.clone().add(0, (this.lines.size() / 2) * distance, 0);
        for (String line : this.lines)
        {
            ids.addAll(showLine(first.clone(), line));
            first.subtract(0, distance, 0);
        }
        showing = true;
        this.location = loc;
    }

    public void show(Location loc, long ticks) {
        show(loc);
        new BukkitRunnable() {
            @Override
            public void run() {
                destroy();
            }
        }.runTaskLater(SamaGamesAPI.get().getPlugin(), ticks);
    }

    public void destroy() {
        if (showing == false) {
            try {
                throw new Exception("Isn't showing!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int[] ints = new int[this.ids.size()];
        for (int j = 0; j < ints.length; j++) {
            ints[j] = ids.get(j);
        }
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(ints);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
        showing = false;
        this.location = null;
    }

}
