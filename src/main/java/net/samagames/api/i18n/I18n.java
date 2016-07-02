package net.samagames.api.i18n;

import java.util.UUID;

public interface I18n
{
    /**
     * Define a project to load, by default it's false for all
     * @param project The project category to load
     * @param value true to load at player join, false otherwise
     */
    void setLanguagesToLoad(ProjectNames project, boolean value);

    /**
     * Know if the project is actually loading at player join
     * @param project The project to know about
     * @return
     */
    boolean isLanguagesLoading(ProjectNames project);

    /**
     * Get a string localized with the given player language
     * @return Localized string
     */
    String localize(String text, UUID player);
}
