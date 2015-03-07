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

        for(int i = -1; i > -19; i--)
        {
            lines.put(i, null);
        }
    }

    public void setLine(int nb, String line)
    {
        if(nb >= 0 || nb < -19)
            throw new IndexOutOfBoundsException("Scoreboard line :" + nb + " - [-1;-18]");

        VScore remove = getScore(lines.get(nb));
        scores.remove(remove);
        VScore add = getScore(line);
        add.setScore(nb);
        lines.put(nb, line);

       // replaceScore(remove, add);*/
    }

    public void updateLines()
    {
        String old = toggleName();
        for(OfflinePlayer op : receivers)
        {
            if(op.isOnline())
            {
                create(op.getPlayer());
                updateScore(op.getPlayer());
                displayToSidebar(op.getPlayer());
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
