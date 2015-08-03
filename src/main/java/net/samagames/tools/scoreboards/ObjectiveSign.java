package net.samagames.tools.scoreboards;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

/**
 * Created by Geekpower14 on 02/01/2015.
 */
public class ObjectiveSign extends VObjective{

    public HashMap<Integer, String> lines = new HashMap<>();

    public ObjectiveSign(String name, String displayName) {
        super(name, displayName);

        for(int i = 0; i < 19; i++)
        {
            lines.put(i, null);
        }
    }

    public void setLine(int nb, String line)
    {
        /*if(nb < 0 || nb >=19)
        {
            Bukkit.getLogger().warning("Scoreboard line :" + nb + " - [0;18]");
        }*/

        VScore remove = getScore(lines.get(nb));
        scores.remove(remove);
        VScore add = getScore(line);
        add.setScore(nb);
        lines.put(nb, line);

        // replaceScore(remove, add);*/
    }

    public void updateLines()
    {
        updateLines(true);
    }

    public void updateLines(boolean inverse)
    {
        String old = toggleName();
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
            {
                create(op.getPlayer());
                updateScore(op.getPlayer(), inverse);
                displayTo(op.getPlayer(), location.getLocation());
                RawObjective.removeObjective(op.getPlayer(), old);
                //remove(op.getPlayer());
            }
        }
    }

    protected void replaceScore(VScore remove, VScore add)
    {
        scores.remove(remove);
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
            {
                RawObjective.updateScoreObjective(op.getPlayer(), this, add);
                RawObjective.removeScoreObjective(op.getPlayer(), this, remove);
            }
        }
    }


}
