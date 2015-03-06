package net.samagames.samagamesapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {

    public static Location str2loc(String loc) {
        if (loc == null)
            return null;
        String[] loca = loc.split(", ");

        return new Location(Bukkit.getServer().getWorld(loca[0]), Double.parseDouble(loca[1]), Double.parseDouble(loca[2]), Double.parseDouble(loca[3]), Float.parseFloat(loca[4]), Float.parseFloat(loca[5]));
    }

    public static String loc2str(Location loc) {
        return "" + loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch();
    }

    public static Boolean hasPermission(Player p, String perm) {
        if (perm.equalsIgnoreCase(""))
            return true;
        if (p.isOp())
            return true;
        return p.hasPermission("UpperVoid.admin") || p.hasPermission(perm);

    }

    public static int getClosestChestSize(int size) {
        if (size % 9 == 0)
            return size;
        else
            return (int) (Math.ceil(size / 9) * 9);
    }

}
