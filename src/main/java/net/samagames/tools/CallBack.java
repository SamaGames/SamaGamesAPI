package net.samagames.tools;

/**
 * CallBack class
 *
 * @param <V> Data type
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface CallBack<V>
{
    /**
     * Function called after action
     *
     * @param data Data returned by the action
     * @param error Error
     */
    void done(V data, Throwable error);
}