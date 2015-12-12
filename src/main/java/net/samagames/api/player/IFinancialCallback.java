package net.samagames.api.player;

/**
 * Financial callback class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IFinancialCallback
{
    /**
     * Fired after financial operation
     *
     * @param newAmount New amount of money
     * @param difference Difference of money between before and
     *                   after the operation
     * @param error {@link Throwable} error if the operation failed
     */
	void done(long newAmount, long difference, Throwable error);
}