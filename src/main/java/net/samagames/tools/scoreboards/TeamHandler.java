package net.samagames.tools.scoreboards;

import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import net.samagames.tools.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class TeamHandler
{
    private ConcurrentLinkedQueue<VTeam> teams;
    private List<OfflinePlayer> receivers;

    /**
     * Constructor
     */
    public TeamHandler()
    {
        this.teams = new ConcurrentLinkedQueue<>();
        this.receivers = new ArrayList<>();
	}

    /**
     * Add receiver
     *
     * @param offlinePlayer Player
     *
     * @return {@code true} if success
     */
    public boolean addReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;

        this.removeFromAllTeams(offlinePlayer);

        this.receivers.add(offlinePlayer);
        Player p = offlinePlayer.getPlayer();
        this.sendAllTeams(p);

        return true;
    }

    /**
     * Remove receiver
     *
     * @param offlinePlayer Player
     *
     * @return {@code true} if success
     */
    public boolean removeReceiver(OfflinePlayer offlinePlayer)
    {
        this.receivers.remove(offlinePlayer);
        this.removeFromAllTeams(offlinePlayer);

        if(offlinePlayer.isOnline())
            this.removeAllTeams(offlinePlayer.getPlayer());

        return true;
    }

    /**
     * Create a new scoreboard team
     *
     * @param name Team name
     * @param display Team display name
     *
     * @return Team instance
     */
    public VTeam createNewTeam(String name, String display)
    {
        return new VTeam(name, display);
    }

    /**
     * Add a given player to the given team
     *
     * @param p Player
     * @param team Team
     *
     * @return {@code true} is success
     */
    public boolean addPlayerToTeam(OfflinePlayer p, VTeam team)
    {
        return this.addPlayerToTeam(p.getName(), team);
    }

    /**
     * Add a given player to the given team
     *
     * @param p Player
     * @param team Team
     *
     * @return {@code true} is success
     */
    public boolean addPlayerToTeam(String p, VTeam team)
    {
        while(this.removeFromAllTeams(p)) {}

        team.addPlayer(p);

        for(OfflinePlayer offlinePlayer : this.receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;

            RawTeam.addPlayerToTeam(offlinePlayer.getPlayer(), team, p);
        }

        return true;
    }

    /**
     * Remove a given player from the given team
     *
     * @param p Player
     * @param team Team
     *
     * @return {@code true} is success
     */
    public boolean removePlayerFromTeam(OfflinePlayer p, VTeam team)
    {
        team.removePlayer(p);

        for(OfflinePlayer offlinePlayer : this.receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;

            RawTeam.removePlayerFromTeam(offlinePlayer.getPlayer(), team, p.getName());
        }

        return true;
    }

    /**
     * Add a team to the scoreboard
     *
     * @param vt Team
     *
     * @return {@code true} is success
     */
    public boolean addTeam(VTeam vt)
    {
        this.teams.add(vt);
        this.sendToAllTeam(vt);

        return true;
    }

    /**
     * Remove a team from the scoreboard
     *
     * @param teamName Team
     *
     * @return {@code true} is success
     */
    public boolean removeTeam(String teamName)
    {
        VTeam vt = this.getTeamByName(teamName);

        if(vt == null)
            return false;

        this.removeToAllTeam(vt);

        return true;
    }
    /**
     * Add a given player to all teams
     *
     * @param offlinePlayer Player
     *
     * @return {@code true} is success
     */
    private boolean removeFromAllTeams(OfflinePlayer offlinePlayer)
    {
        return this.removeFromAllTeams(offlinePlayer.getName());
    }

    /**
     * Add a given player to all teams
     *
     * @param player Player
     *
     * @return {@code true} is success
     */
    private boolean removeFromAllTeams(String player)
    {
        for (VTeam team : teams)
        {
            for (String op : team.getPlayers())
            {
                if (player.equals(op))
                {
                    team.removePlayer(op);

                    for (OfflinePlayer p : this.receivers)
                    {
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

    /**
     * Send scoreboard's teams to a given player
     *
     * @param p Player
     */
    private void sendAllTeams(Player p)
    {
        for (VTeam vt : this.teams)
            this.sendTeam(p, vt);
    }

    /**
     * Remove scoreboard's teams from a given player
     *
     * @param p Player
     */
    private void removeAllTeams(Player p)
    {
        for (VTeam vt : this.teams)
            this.removeTeam(p, vt);
    }

    /**
     * Send a given scoreboard's team to all players
     *
     * @param vt Team
     */
    private void sendToAllTeam(VTeam vt)
    {
        for(OfflinePlayer offlinePlayer : this.receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;

            this.sendTeam(offlinePlayer.getPlayer(), vt);
        }
    }

    /**
     * Remove a given scoreboard's team from all players
     *
     * @param vt Team
     */
    private void removeToAllTeam(VTeam vt)
    {
        for(OfflinePlayer offlinePlayer : this.receivers)
        {
            if(!offlinePlayer.isOnline())
                continue;

            this.removeTeam(offlinePlayer.getPlayer(), vt);
        }
    }

    /**
     * Send a given team to the given player
     *
     * @param p Player
     * @param vt Team
     */
    private void sendTeam(Player p, VTeam vt)
    {
        RawTeam.createTeam(p, vt);
        RawTeam.sendTeam(p, vt);
    }

    /**
     * Remove a given team from the given player
     *
     * @param p Player
     * @param vt Team
     */
    private void removeTeam(Player p, VTeam vt)
    {
        RawTeam.removeTeam(p, vt);
    }

    /**
     * Get the player's scoreboard team
     *
     * @param p Player
     *
     * @return Team
     */
    public VTeam getTeamByPlayer(Player p)
    {
        for (VTeam n : this.teams)
            if (n.contains(p.getName()))
                return n;

        return null;
    }

    /**
     * Get a scoreboard team by it's name
     *
     * @param name Team's name
     *
     * @return Team
     */
    public VTeam getTeamByName(String name)
    {
        for (VTeam n : this.teams)
            if (n.getName().equals(name))
                return n;

        return null;
    }

    public static class RawTeam
    {
        public static void createTeam(Player p, VTeam team)
        {
            Reflection.sendPacket(p, makePacket(team, new ArrayList<>(), 0));
        }

        public static void sendTeam(Player p, VTeam team)
        {
            Reflection.sendPacket(p, makePacket(team, 3));
        }

        public static void removeTeam(Player p, VTeam team)
        {
            Reflection.sendPacket(p, makePacket(team, new ArrayList<>(), 1));
        }

        public static void changeTeam(Player p, VTeam team)
        {
            Reflection.sendPacket(p, makePacket(team, new ArrayList<>(), 2));
        }

        public static void addPlayerToTeam(Player receiver, VTeam team, Player toadd)
        {
            addPlayerToTeam(receiver, team, toadd.getName());
        }

        public static void addPlayerToTeam(Player receiver, VTeam team, String toadd)
        {
            Reflection.sendPacket(receiver, makePacket(team, Collections.singletonList(toadd), 3));
        }

        public static void removePlayerFromTeam(Player receiver, VTeam team, Player toremove)
        {
            removePlayerFromTeam(receiver, team, toremove.getName());
        }

        public static void removePlayerFromTeam(Player receiver, VTeam team, String toremove)
        {
            if (team.players.contains(toremove))
                Reflection.sendPacket(receiver, makePacket(team, Collections.singletonList(toremove), 4));
        }

        private static Object makePacket(VTeam team, List<String> news, int n)
        {
            //0: Création de team
            //1: Suppression de team
            //2: Changements infos de la team
            //3: Ajout d'un joueur
            //4: Suppression d'un joueur

            if (news == null)
                news = new ArrayList<>();

            try
            {
                PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

                Reflection.setValue(packet, "a", team.getRealName()); // Team display name
                Reflection.setValue(packet, "b", team.getDisplayName()); // Team display name
                Reflection.setValue(packet, "c", team.getPrefix()); // Team prefix
                Reflection.setValue(packet, "d", team.getSuffix()); // Team suffix
                Reflection.setValue(packet, "e", team.getHideToOtherTeams() ? "hideForOtherTeams" : "always"); // Name tag visible
                Reflection.setValue(packet, "f", "never"); // Collision rule
                Reflection.setValue(packet, "g", news.size()); // Player count
                Reflection.setValue(packet, "h", news); // Players
                Reflection.setValue(packet, "i", n); // Action id
                Reflection.setValue(packet, "j", 0); // Friendly fire

                return packet;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        private static Object makePacket(VTeam team, int n)
        {
            return makePacket(team, team.getPlayers(), n);
        }
    }

    public class VTeam
    {
        private String name = "";
        private String realName = "";
        private String display = "";
        private String prefix = ChatColor.GRAY + "";
        private String suffix = "";
        private boolean hideToOtherTeams = false;

        private CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList<>();

        public VTeam(String name, String display)
        {
            this.name = name;
            this.display = display;
        }

        public VTeam(String name, String realName, String display, String prefix, String suffix)
        {
            this.name = name;
            this.realName = realName;
            this.display = display;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public String getName()
        {
            return this.name;
        }

        public String getDisplayName()
        {
            return this.display;
        }

        public void setDisplayName(String dn)
        {
            this.display = dn;
        }

        public String getPrefix()
        {
            return this.prefix;
        }

        public void setPrefix(String p)
        {
            this.prefix = p.substring(0, Math.min(p.length(), 16));
        }

        public String getSuffix()
        {
            return this.suffix;
        }

        public void setSuffix(String s)
        {
            this.suffix = s.substring(0, Math.min(s.length(), 16));
        }

        public boolean contains(OfflinePlayer op)
        {
            return this.players.contains(op.getName());
        }

        public boolean contains(String op)
        {
            return this.players.contains(op);
        }

        public List<String> getPlayers()
        {
            return this.players;
        }

        public void addPlayer(OfflinePlayer op)
        {
            this.players.add(op.getName());
        }

        public void addPlayer(String op)
        {
            this.players.add(op);
        }

        public void removePlayer(OfflinePlayer op)
        {
            if (this.contains(op))
                this.players.remove(op.getName());
        }

        public void removePlayer(String op)
        {
            this.players.remove(op);
        }

        public int getSize()
        {
            return this.players.size();
        }

        public String getRealName()
        {
            return this.realName;
        }

        public void setRealName(String realName)
        {
            this.realName = realName;
        }

        public void setHideToOtherTeams(boolean hideToOtherTeams)
        {
            this.hideToOtherTeams = hideToOtherTeams;
        }

        public boolean getHideToOtherTeams()
        {
            return this.hideToOtherTeams;
        }
    }
}
