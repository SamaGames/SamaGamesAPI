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
     * Get a string localized with the given player language
     * @return Localized string
     */
    String localize(int sentenceId, UUID player, Object... params) throws Exception;
}
