package net.samagames.core.api.games;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.samagames.api.games.IGameProperties;
import net.samagames.core.APIPlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;

public class GameProperties implements IGameProperties
{
    private final HashMap<String, String> options;

    private String mapName;
    private int minSlots;
    private int maxSlots;

    public GameProperties()
    {
        this.options = new HashMap<>();
        this.reload();
    }

    public void reload()
    {
        this.options.clear();

        try
        {
            File file = new File(APIPlugin.getInstance().getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), "game.json");

            if(!file.exists())
                return;

            JsonObject rootJson = new JsonParser().parse(new FileReader(file)).getAsJsonObject();

            this.mapName = rootJson.get("map-name").getAsString();
            this.minSlots = rootJson.get("min-slots").getAsInt();
            this.maxSlots = rootJson.get("max-slots").getAsInt();

            JsonArray optionsJson = rootJson.get("options").getAsJsonArray();

            for(int i = 0; i < optionsJson.size(); i++)
            {
                JsonObject optionJson = optionsJson.get(i).getAsJsonObject();
                this.options.put(optionJson.get("key").getAsString(), optionJson.get("value").getAsString());
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            APIPlugin.log(Level.SEVERE, "Can't open the game properties file. Abort start!");

            Bukkit.shutdown();
        }
    }

    public String getMapName()
    {
        return this.mapName;
    }

    public int getMinSlots()
    {
        return this.minSlots;
    }

    public int getMaxSlots()
    {
        return this.maxSlots;
    }

    public String getOption(String key)
    {
        if(this.options.containsKey(key))
            return this.options.get(key);
        else
            return null;
    }

    public HashMap<String, String> getOptions()
    {
        return this.options;
    }
}
