package net.samagames.api.options;

/**
 * Created by Silva on 15/11/2015.
 */
public interface ServerOptions {


    /**
     * Get the actual state of the rank display
     * @return the state
     */
    boolean hasRankTabColor();

    /**
     * Set if the tab color of rank is actived or not (for all players).
     * @param enable (true: all see the rank/ false: nun rank is display)
     */
    void setRankTabColorEnable(boolean enable);

    /**
     * Get if the basic minecraft nature is active or not.
     * @return true if the nature is active and false if not.
     */
    boolean hasActiveNature();

    /**
     * Set if the basic minecraft nature is active.
     * @param enable true to activate the nature and false to disable it.
     */
    void setActiveNature(boolean enable);
}