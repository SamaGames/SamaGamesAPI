package net.samagames.api.achievements;

import java.util.ArrayList;
import java.util.UUID;

public interface AchievementManager
{
    /**
     * Recharge la liste locale des objectifs
     */
    public void reloadList();

    /**
     * Renvoi un objectif selon l'identifiant unique donné
     * @param uuid ID unique de l'objectif
     * @return L'objectif demandé
     */
    public Achievement getAchievementByID(UUID uuid);

    /**
     * Renvoi une catégorie d'objectifs selon l'identifiant unique donné
     * @param uuid ID unique de la catégorie
     * @return La catégorie demandée
     */
    public AchievementCategory getAchievementCategoryByID(UUID uuid);

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
}
