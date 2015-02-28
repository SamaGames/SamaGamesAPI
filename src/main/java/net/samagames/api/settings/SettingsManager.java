package net.samagames.api.settings;

import java.util.Map;
import java.util.UUID;

public interface SettingsManager {
    public Map<String, String> getSettings(UUID player);

    public String getSetting(UUID player, String setting);

    public void setSetting(UUID player, String setting, String value);
}
