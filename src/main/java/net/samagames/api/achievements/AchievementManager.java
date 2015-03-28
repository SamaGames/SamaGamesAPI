package net.samagames.api.achievements;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface AchievementManager
{
    /**
     * Recharge la liste locale des objectifs
     */
    public void reloadList();

    /**
     * Incrémente la progression de l'achievement pour un joueur donné
     * @param player Le joueur en question
     * @param achievement L'objectif en question
     * @return La nouvelle valeur de l'objectif, après l'incrémentation
     */
    public int incrementAchievement(Player player, Achievement achievement);

    /**
     * Renvoi un objectif selon son nom
     * @param name Nom de l'objectif
     * @return L'objectif demandé
     */
    public Achievement getAchievementByName(String name);

    /**
     * Renvoi une catégorie d'objectifs selon l'identifiant unique donné
     * @param name Nom de la catégorie
     * @return La catégorie demandée
     */
    public AchievementCategory getAchievementCategoryByName(String name);

    /**
     * Renvoi la liste des objectifs enregistrés dans la base de donnée
     * @return La liste des objectifs
     */
    public ArrayList<Achievement> getAchievements();

    /**
     * Renvoi la liste des catégories d'objectifs enregistrés dans la base de donnée
     * @return La liste des catégories
     */
    public ArrayList<AchievementCategory> getAchievementsCategories();

    /**
     * Renvoi si l'objectif est débloqué pour le joueur, ou non
     * @param player Le joueur en question
     * @param achievement L'objectif à tester
     * @return Si oui ou non l'objectif est débloqué
     */
    public boolean isUnlocked(Player player, Achievement achievement);

    /**
     * Renvoi si l'objectif est débloqué pour le joueur, ou non
     * @param player Le joueur en question
     * @param achievement L'objectif à tester
     * @return Si oui ou non l'objectif est débloqué
     */
    public boolean isUnlocked(Player player, String achievement);
}
