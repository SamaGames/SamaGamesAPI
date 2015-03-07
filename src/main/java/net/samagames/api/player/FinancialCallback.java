package net.samagames.api.player;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public interface FinancialCallback<V> {

	/**
	 * Fonction appelée une fois une transaction financière terminée
	 * @param newAmount Nouveau montant après crédit/débit
	 * @param difference Montant exact crédité/débité (+ = crédit / - = débit)
	 * @param error
	 */
	public void done(V newAmount, V difference, Throwable error);
}