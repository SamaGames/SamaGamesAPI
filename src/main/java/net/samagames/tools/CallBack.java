package net.samagames.tools;

/**
 * Created by Silva on 20/10/2015.
 */
public interface CallBack<V> {

    /**
     * Function called after action
     * @param data Data returned by the action
     * @param error
     */
    void done(V data, Throwable error);
}