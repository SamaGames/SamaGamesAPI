package net.samagames.api.settings;

import java.util.Map;
import java.util.UUID;


public interface ISettingsManager {

	/**
	 * Renvoie l'ensemble des paramètres du joueur
	 * @param player Joueur à récupérer
	 * @return Une map "Paramètre - Valeur" des paramètres du joueur
	 */
    public Map<String, String> getSettings(UUID player);

	/**
	 * Renvoie un paramètre en particulier
	 * @param player Joueur à récupérer
	 * @param setting Paramètre à récupérer
	 * @return Valeur du paramètre pour le joueur (null si non existant)
	 */
    public String getSetting(UUID player, String setting);

	/**
	 * Définit la valeur d'un paramètre pour un joueur
	 * @param player	Joueur a modifier
	 * @param setting	Paramètre a modifier
	 * @param value		Valeur à définir
	 */
    public void setSetting(UUID player, String setting, String value);

	/**
	 * Définit la valeur d'un paramètre pour un joueur
	 * @param player	Joueur a modifier
	 * @param setting	Paramètre a modifier
	 * @param value		Valeur à définir
	 * @param callback  Callback
	 */
	public void setSetting(UUID player, String setting, String value, Runnable callback);

	/**
	 * Permet de savoir si une option est activée
	 * @param player	Joueur a récupérer
	 * @param option	Option à récupérer
	 * @param def		Valeur par défaut si l'option n'est pas définir
	 * @return la valeur de l'option si elle est définie, <code>def</code> sinon.
	 */
	public default boolean isEnabled(UUID player, String option, boolean def) {
		String val = getSetting(player, option);
		if (val == null)
			return def;

		return Boolean.valueOf(val);
	}
}
