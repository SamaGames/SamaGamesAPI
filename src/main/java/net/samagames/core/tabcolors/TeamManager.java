package net.samagames.core.tabcolors;

import net.samagames.core.APIPlugin;
import net.samagames.permissionsapi.PermissionsAPI;
import net.samagames.permissionsapi.permissions.PermissionGroup;
import net.samagames.permissionsapi.permissions.PermissionUser;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TeamManager {

    /**
     * The escape sequence for minecraft special chat codes
     */
    public static final char ESCAPE = '\u00A7';
    public List<PermissionGroup> groups = new ArrayList<>();
	private final APIPlugin plugin;

    public TeamHandler teamHandler;

    public Executor executor = Executors.newSingleThreadExecutor();

    public TeamManager(APIPlugin pl) {
        plugin = pl;

        teamHandler = new TeamHandler();

        if (!pl.isDatabaseEnabled())
            return;

        for (PermissionGroup g : PermissionsBukkit.getApi().getManager().getGroupsCache().values())
            groups.add(g);

        for (PermissionGroup pg : groups) {
            TeamHandler.VTeam vt = teamHandler.createNewTeam(pg.getGroupName(), "");

            if (PermissionsBukkit.getDisplay(pg) != null)
                vt.setPrefix(PermissionsBukkit.getDisplay(pg));
            if (PermissionsBukkit.getDisplay(pg) != null)
                vt.setDisplayName(PermissionsBukkit.getDisplay(pg));
            if (PermissionsBukkit.getSuffix(pg) != null)
                vt.setSuffix(PermissionsBukkit.getSuffix(pg));

            teamHandler.addTeam(vt);
            APIPlugin.log("[TeamRegister] Team " + pg.getGroupName() + " ajouté.");
        }
    }

    /**
     * Replace all of the color codes (prepended with &) with the corresponding color code.
     * This uses raw char arrays so it can be considered to be extremely fast.
     *
     * @param text
     * @return
     */
    public static String replaceColors(String text) {
        char[] chrarray = text.toCharArray();

        for (int index = 0; index < chrarray.length; index++) {
            char chr = chrarray[index];

            // Ignore anything that we don't want
            if (chr != '&') {
                continue;
            }

            if ((index + 1) == chrarray.length) {
                // we are at the end of the array
                break;
            }

            // get the forward char
            char forward = chrarray[index + 1];

            // is it in range?
            if ((forward >= '0' && forward <= '9') || (forward >= 'a' && forward <= 'f') || (forward >= 'k' && forward <= 'r')) {
                // It is! Replace the char we are at now with the escape sequence
                chrarray[index] = ESCAPE;
            }
        }

        // Rebuild the string and return it
        return new String(chrarray);
    }

    public static List<Player> getOnline() {
        List<Player> list = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            list.addAll(world.getPlayers());
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Takes a string and replaces &# color codes with ChatColors
     *
     *
     * @return
     */

    public void playerLeave(final Player p) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                teamHandler.removeReceiver(p);
            }
        });

    }

    public void playerJoin(final Player p) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                teamHandler.addReceiver(p);

                final PermissionUser user = PermissionsBukkit.getApi().getUser(p.getUniqueId());
                final String prefix = PermissionsBukkit.getDisplay(user);

                TeamHandler.VTeam vtt = teamHandler.getTeamByPrefix(prefix);
                if (vtt == null) {
                    vtt = teamHandler.getTeamByName("Joueur");
                }

                teamHandler.addPlayerToTeam(p, vtt);
            }
        });
    }

}
