package net.samagames.tools.scoreboards;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.samagames.tools.Reflection;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Geekpower14 on 01/01/2015.
 */
public class VObjective {

    protected String name;
    protected String displayName;
    protected EnumScoreboardHealthDisplay format;

    protected List<OfflinePlayer> receivers = new ArrayList<>();

    protected ConcurrentLinkedQueue<VScore> scores = new ConcurrentLinkedQueue<>();

    public VObjective(String name, String displayName)
    {
        format = EnumScoreboardHealthDisplay.INTEGER;
        this.name = name;
        this.displayName = displayName;
    }

    protected String toggleName()
    {
        String old = name;
        if(name.endsWith("1"))
        {
            name = name.substring(0, name.length()-1);
        }else
        {
            name += "1";
        }
        return old;
    }

    public boolean addReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;
        receivers.add(offlinePlayer);
        Player p = offlinePlayer.getPlayer();

        init(p);
        updateScore(p);
        return true;
    }

    public void removeReceiver(OfflinePlayer offlinePlayer)
    {
        receivers.remove(offlinePlayer);
        if(offlinePlayer.isOnline())
            remove(offlinePlayer.getPlayer());
    }

    protected void init(Player receiver)
    {
        create(receiver);
        displayToSidebar(receiver);
    }

    protected void create(Player receiver)
    {
        RawObjective.createObjective(receiver, this);
    }

    protected void displayToSidebar(Player receiver)
    {
        RawObjective.displayObjective(receiver, getName());
    }

    protected void remove(Player receiver)
    {
        RawObjective.removeObjective(receiver, this);
    }



    public void updateScore(String score)
    {
        updateScore(getScore(score));
    }

    public void updateScore(VScore score)
    {
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
                RawObjective.updateScoreObjective(op.getPlayer(), this, score);
        }
    }

    protected void updateScore(Player p)
    {
        RawObjective.updateScoreObjective(p, this, false);
    }

    protected void updateScore(Player p, boolean inverse)
    {
        RawObjective.updateScoreObjective(p, this, inverse);
    }

    public void updateScore()
    {
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
                RawObjective.updateScoreObjective(op.getPlayer(), this, false);
        }
    }

    public void update()
    {
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
                RawObjective.updateObjective(op.getPlayer(), this);
        }
    }

    public void removeScore(String score)
    {
        VScore score1 = getScore(score);
        removeScore(score1);
    }

    public void removeScore(VScore score)
    {
        scores.remove(score);
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
                RawObjective.removeScoreObjective(op.getPlayer(), this, score);
        }
    }

    public ConcurrentLinkedQueue<VScore> getScores()
    {
        return scores;
    }

    public VScore getScore(String player)
    {
        for(VScore score : scores)
        {
            if(score.getPlayerName().equals(player))
            {
                return score;
            }
        }
        VScore score = new VScore(player, 0);
        scores.add(score);
        return score;
    }

    public boolean containsScore(String player)
    {
        for(VScore score : scores)
        {
            if(score.getPlayerName().equals(player))
            {
                return true;
            }
        }
        return false;
    }

    public String getName()
    {
        return name;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public EnumScoreboardHealthDisplay getFormat()
    {
        return format;
    }

    public static class RawObjective
    {
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

        /** Objective Management **/
        public static void createObjective(Player p, VObjective objective)
        {
            sendPacket(makeScoreboardObjectivePacket(0, objective.getName(), objective.getDisplayName(), objective.getFormat()), p);
        }

        public static void updateObjective(Player p, VObjective objective)
        {
            sendPacket(makeScoreboardObjectivePacket(2, objective.getName(), objective.getDisplayName(), objective.getFormat()), p);
        }

        public static void removeObjective(Player p, VObjective objective)
        {
            sendPacket(makeScoreboardObjectivePacket(1, objective.getName(), objective.getDisplayName(), objective.getFormat()), p);
        }

        public static void removeObjective(Player p, String name)
        {
            sendPacket(makeScoreboardObjectivePacket(1, name, "", EnumScoreboardHealthDisplay.INTEGER), p);
        }

        /** Objective Display **/
        public static void displayObjective(Player p, String name)
        {
            sendPacket(makeScoreboardDiplayPacket(name), p);
        }

        /** Objective Score Management **/

        public static void createScoreObjective(Player p, VObjective objective)
        {
            updateScoreObjective(p, objective, false);
        }

        public static void createScoreObjective(Player p, VObjective objective, VScore score)
        {
            updateScoreObjective(p, objective, score);
        }

        public static void updateScoreObjective(Player p, VObjective objective, boolean inverse)
        {
            if(!inverse)
            {
                for(VScore score : objective.getScores())
                {
                    updateScoreObjective(p, objective, score);
                }
                return;
            }
            int i = 0;
            for(VScore score : objective.getScores())
            {
                updateScoreObjective(p, objective, score, objective.getScores().size()-score.getScore()-1);
                i++;
            }
        }

        public static void updateScoreObjective(Player p, VObjective objective, VScore score)
        {
            sendPacket(makeScoreboardScorePacket(objective.getName(), PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE, score.getPlayerName(), score.getScore()), p);
        }

        public static void updateScoreObjective(Player p, VObjective objective, VScore score, int score_int)
        {
            sendPacket(makeScoreboardScorePacket(objective.getName(), PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE, score.getPlayerName(), score_int), p);
        }

        public static void removeScoreObjective(Player p, VObjective objective)
        {
            for(VScore score : objective.getScores())
            {
                removeScoreObjective(p, objective, score);
            }
        }

        public static void removeScoreObjective(Player p, VObjective objective, VScore score)
        {
            sendPacket(makeScoreboardScorePacket(objective.getName(), PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE, score.getPlayerName(), 0), p);
        }


        public static PacketPlayOutScoreboardScore makeScoreboardScorePacket(String objectiveName, PacketPlayOutScoreboardScore.EnumScoreboardAction action, String scoreName, int scoreValue)
        {
            if(objectiveName == null)
                objectiveName = "";

            PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();
            try {
                Reflection.setValue(packet, "a", scoreName); //Nom du joueur
                Reflection.setValue(packet, "b", objectiveName); //Nom de l'objective
                Reflection.setValue(packet, "c", scoreValue); //Valeur du score
                Reflection.setValue(packet, "d", action); //Action du packet
            } catch (Exception e) {
                e.printStackTrace();
            }

            return packet;
        }

        public static PacketPlayOutScoreboardObjective makeScoreboardObjectivePacket(int action, String objectiveName, String objectiveDispleyName,  EnumScoreboardHealthDisplay format)
        {
            PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
            try {
                Reflection.setValue(packet, "a", objectiveName); //Nom de l'objective
                Reflection.setValue(packet, "b", objectiveDispleyName); //Nom affiché de l'objective
                Reflection.setValue(packet, "c", format); //Affichage des données Nombre/Coeurs
                Reflection.setValue(packet, "d", action); //Action à effectuer - 0: Create 1: Remove 2: Update
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packet;
        }

        public static PacketPlayOutScoreboardDisplayObjective makeScoreboardDiplayPacket(String objectiveName)
        {

            PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
            try {
                Reflection.setValue(packet, "a", 1); //Emplacement de l'objective - 0 = list, 1 = sidebar, 2 = belowName
                Reflection.setValue(packet, "b", objectiveName); //Nom de l'objective
            } catch (Exception e) {
                e.printStackTrace();
            }

            return packet;
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

    }

    public class VScore{
        private String playerName;
        private int score;

        public VScore(String player, int score)
        {
            this.playerName = player;
            this.score = score;
        }

        public int getScore()
        {
            return this.score;
        }

        public void setScore(int score)
        {
            //int old = this.score;
            this.score = score;
           /*if(old != this.score)
            {
                //TODO: HANDLE si besoin de updateScore auto
            }*/
        }

        public void removeScore(int score)
        {
            this.setScore(this.getScore() - score);
        }

        public void incrementScore()
        {
            this.setScore(this.getScore() + 1);
        }

        public void addScore(int score)
        {
            this.setScore(this.getScore() + score);
        }

        public String getPlayerName()
        {
            return this.playerName;
        }
    }

}

