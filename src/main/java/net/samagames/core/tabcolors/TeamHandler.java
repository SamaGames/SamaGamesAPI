package net.samagames.core.tabcolors;

import net.minecraft.server.v1_8_R2.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R2.ScoreboardTeamBase;
import net.samagames.tools.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Geekpower14 on 11/01/2015.
 */
public class TeamHandler {

    public ConcurrentLinkedQueue<VTeam> teams = new ConcurrentLinkedQueue<>();
    protected List<OfflinePlayer> receivers = new ArrayList<>();

    public TeamHandler() {
	}

    public boolean addReceiver(OfflinePlayer offlinePlayer) {
        if(!offlinePlayer.isOnline())
            return false;

        removeFromAllTeams(offlinePlayer);

        receivers.add(offlinePlayer);
        Player p = offlinePlayer.getPlayer();
        sendAllTeams(p);

        return true;
    }

    public VTeam createNewTeam(String name, String display)
    {
        return new VTeam(name, display);
    }

    public boolean removeReceiver(OfflinePlayer offlinePlayer)
    {
        receivers.remove(offlinePlayer);
        removeFromAllTeams(offlinePlayer);
        if(offlinePlayer.isOnline())
            removeAllTeams(offlinePlayer.getPlayer());
        return true;
    }

    public boolean addPlayerToTeam(OfflinePlayer p, VTeam team)
    {
        while(removeFromAllTeams(p)){}

        team.addPlayer(p);
        for(OfflinePlayer offlinePlayer : receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;
            RawTeam.addPlayerToTeam(offlinePlayer.getPlayer(), team, p.getName());
        }
        return true;
    }

    public boolean removePlayerFromTeam(OfflinePlayer p, VTeam team)
    {
        team.removePlayer(p);
        for(OfflinePlayer offlinePlayer : receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;
            RawTeam.removePlayerFromTeam(offlinePlayer.getPlayer(), team, p.getName());
        }
        return true;
    }

    protected boolean removeFromAllTeams(OfflinePlayer offlinePlayer) {
        String player = offlinePlayer.getName();

        for (VTeam team : teams) {
            for (String op : team.getPlayers()) {
                if (player.equals(op)) {
                    team.removePlayer(op);
                    for (OfflinePlayer p : receivers) {
                        if(!p.isOnline())
                            continue;

                        RawTeam.removePlayerFromTeam(p.getPlayer(), team, op);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean addTeam(VTeam vt)
    {
        teams.add(vt);
        sendToAllTeam(vt);
        return true;
    }

    public boolean removeTeam(String teamName)
    {
        VTeam vt = getTeamByName(teamName);
        if(vt == null)
            return false;

        removeToAllTeam(vt);

        return true;
    }

    protected void sendAllTeams(Player p)
    {
        for (VTeam vt : teams) {
            sendTeam(p, vt);
        }
    }

    protected void removeAllTeams(Player p)
    {
        for (VTeam vt : teams) {
            removeTeam(p, vt);
        }
    }

    protected void sendToAllTeam(VTeam vt)
    {
        for(OfflinePlayer offlinePlayer : receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;
            sendTeam(offlinePlayer.getPlayer(), vt);
        }
    }

    protected void removeToAllTeam(VTeam vt)
    {
        for(OfflinePlayer offlinePlayer : receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;
            removeTeam(offlinePlayer.getPlayer(), vt);
        }
    }

    protected void sendTeam(Player p, VTeam vt)
    {
        RawTeam.createTeam(p, vt);
        RawTeam.sendTeam(p, vt);
    }

    protected void removeTeam(Player p, VTeam vt)
    {
        RawTeam.removeTeam(p, vt);
    }

    public VTeam getTeamByPlayer(Player p) {
        for (VTeam n : teams) {
            if (n.contains(p.getName()))
                return n;
        }
        return null;
    }

    public VTeam getTeamByName(String name) {
        for (VTeam n : teams) {
            if (n.getName().equals(name))
                return n;
        }

        return null;
    }

    public static class RawTeam {

        private static Method getEntityHandle;
        private static Field getPlayerConnection;
        private static Method sendPacket;

        static {
            try {

                getEntityHandle = Reflection.getOBCClass("entity.CraftPlayer").getMethod("getHandle");
                getPlayerConnection = Reflection.getNMSClass("EntityPlayer").getDeclaredField("playerConnection");
                sendPacket = Reflection.getNMSClass("PlayerConnection").getMethod("sendPacket", Reflection.getNMSClass("Packet"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void createTeam(Player p, VTeam team) {
            sendPacket(makePacket(team, new ArrayList<String>(), 0), p);
        }

        public static void sendTeam(Player p, VTeam team) {
            sendPacket(makePacket(team, 3), p);
        }

        public static void removeTeam(Player p, VTeam team) {
            sendPacket(makePacket(team, new ArrayList<String>(), 1), p);
        }

        public static void changeTeam(Player p, VTeam team) {
            sendPacket(makePacket(team, new ArrayList<String>(), 2), p);
        }

        public static void addPlayerToTeam(Player receiver, VTeam team, Player toadd) {
            addPlayerToTeam(receiver, team, toadd.getName());
        }

        public static void addPlayerToTeam(Player receiver, VTeam team, String toadd) {
            sendPacket(makePacket(team, Arrays.asList(toadd), 3), receiver);
        }

        public static void removePlayerFromTeam(Player receiver, VTeam team, Player toremove) {
            removePlayerFromTeam(receiver, team, toremove.getName());
        }

        public static void removePlayerFromTeam(Player receiver, VTeam team, String toremove) {
            if (team.players.contains(toremove))
                sendPacket(makePacket(team, Arrays.asList(toremove), 4), receiver);
        }

        private static PacketPlayOutScoreboardTeam makePacket(VTeam team, List<String> news, int n) {
            //0: Création de team
            //1: Suppression de team
            //2: Changements infos de la team
            //3: Ajout d'un joueur
            //4: Suppression d'un joueur

            if (news == null) {
                news = new ArrayList<>();
            }

            PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
            try {
                ReflectionUtilities.setValue(packet, "a", team.getName());
                ReflectionUtilities.setValue(packet, "b", team.getDisplayName());
                ReflectionUtilities.setValue(packet, "c", team.getPrefix());
                ReflectionUtilities.setValue(packet, "d", team.getSuffix());
                ReflectionUtilities.setValue(packet, "e", ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e);
                ReflectionUtilities.setValue(packet, "i", 0);
                ReflectionUtilities.setValue(packet, "f", -1);
                ReflectionUtilities.setValue(packet, "g", (Collection) news);
                ReflectionUtilities.setValue(packet, "h", n);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return packet;
        }

        private static PacketPlayOutScoreboardTeam makePacket(VTeam team, int n) {
            return makePacket(team, team.getPlayers(), n);
        }

        //simple method for sending a packet to a CraftPlayer
        public static void sendPacket(Object packet, Player player) {
            try {
                Object nms_player = getEntityHandle.invoke(player);
                Object nms_connection = getPlayerConnection.get(nms_player);
                sendPacket.invoke(nms_connection, packet);
                //System .out.println(player.getName() + " recieved packet.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static class ReflectionUtilities {

            /**
             * sets a value of an {@link Object} via reflection
             *
             * @param instance  instance the class to use
             * @param fieldName the name of the {@link java.lang.reflect.Field} to modify
             * @param value     the value to set
             * @throws Exception
             */
            public static void setValue(Object instance, String fieldName, Object value) throws Exception {
                Field field = instance.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance, value);
            }

            /**
             * get a value of an {@link Object}'s {@link java.lang.reflect.Field}
             *
             * @param instance  the target {@link Object}
             * @param fieldName name of the {@link java.lang.reflect.Field}
             * @return the value of {@link Object} instance's {@link java.lang.reflect.Field} with the
             * name of fieldName
             * @throws Exception
             */
            public static Object getValue(Object instance, String fieldName) throws Exception {
                Field field = instance.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(instance);
            }

        }
    }

    public class VTeam {

        private String name = "";
        private String display = "";
        private String prefix = ChatColor.GRAY + "";
        private String suffix = "";

        private CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList<>();

        public VTeam(String name, String display) {
            this.name = name;
            this.display = display;
        }

        public VTeam(String name, String display, String prefix, String suffix) {
            this.name = name;
            this.display = display;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public String getName() {
            return name;
        }

        public String getDisplayName() {
            return display;
        }

        public void setDisplayName(String dn) {
            display = dn;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String p) {
            prefix = p;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String s) {
            suffix = s;
        }

        public boolean contains(OfflinePlayer op) {
            return players.contains(op.getName());
        }

        public boolean contains(String op) {
            return players.contains(op);
        }

        public List<String> getPlayers() {
            return players;
        }

        public void addPlayer(OfflinePlayer op) {
            players.add(op.getName());
        }

        public void removePlayer(OfflinePlayer op) {
            if (contains(op))
                players.remove(op.getName());
        }

        public void removePlayer(String op) {
            players.remove(op);
        }

        public int getSize() {
            return players.size();
        }
    }
}
