package net.samagames.tools.scoreboards;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

/**
 * Objective sign class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class ObjectiveSign extends VObjective
{
    public HashMap<Integer, String> lines = new HashMap<>();

    /**
     * Constructor
     *
     * @param name Objective's name
     * @param displayName Objective's display name
     */
    public ObjectiveSign(String name, String displayName)
    {
        super(name, displayName);

        this.lines = new HashMap<>();

        for(int i = 0; i < 19; i++)
            this.lines.put(i, null);
    }
    
    /**
     * Add a receiver to the objective
     *
     * @param offlinePlayer Player
     */
    public boolean addReceiver(OfflinePlayer offlinePlayer)
    {
        if(!offlinePlayer.isOnline())
            return false;

        this.receivers.add(offlinePlayer);

        Player p = offlinePlayer.getPlayer();

        this.init(p);
        this.updateScore(p, true);

        return true;
    }

    /**
     * Set scoreboard line at the given location
     *
     * @param nb Scoreboard's line
     * @param line Text
     */
    public void setLine(int nb, String line)
    {
        VScore remove = this.getScore(this.lines.get(nb));
        this.scores.remove(remove);

        VScore add = this.getScore(line);
        add.setScore(nb);

        this.lines.put(nb, line);
    }

    /**
     * Update lines to receivers
     */
    public void updateLines()
    {
        this.updateLines(true);
    }

    /**
     * Update lines to receivers
     *
     * @param inverse Inverse the order of lines
     */
    public void updateLines(boolean inverse)
    {
        String old = toggleName();

        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op ->
        {
            this.create(op.getPlayer());
            this.updateScore(op.getPlayer(), inverse);
            this.displayTo(op.getPlayer(), this.location.getLocation());

            RawObjective.removeObjective(op.getPlayer(), old);
        });
    }

    private void replaceScore(VScore remove, VScore add)
    {
        this.scores.remove(remove);

        this.receivers.stream().filter(OfflinePlayer::isOnline).forEach(op ->
        {
            RawObjective.updateScoreObjective(op.getPlayer(), this, add);
            RawObjective.removeScoreObjective(op.getPlayer(), this, remove);
        });
    }
}
