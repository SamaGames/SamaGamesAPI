package net.samagames.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.logging.Level;

/**
 * Json configuration class
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class JsonConfiguration
{
    private final File configurationFile;

    /**
     * Constructor
     *
     * @param configurationFile File of the Json file
     */
    public JsonConfiguration(File configurationFile)
    {
        this.configurationFile = configurationFile;
    }

    /**
     * Constructor
     *
     * @param path Path to the Json file
     */
    public JsonConfiguration(String path)
    {
        this(new File(path));
    }

    /**
     * Load the configuration
     *
     * @return Json object who represents root
     * configuration
     */
    public JsonObject load()
    {
        try
        {
            if(!this.configurationFile.exists())
                return null;

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.configurationFile), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String currentLine;

            while((currentLine = br.readLine()) != null)
            {
                builder.append(currentLine);
            }

            br.close();

            return new JsonParser().parse(builder.toString()).getAsJsonObject();
        }
        catch (IOException ex)
        {
            Bukkit.getLogger().log(Level.SEVERE, "Can't load file '" + this.configurationFile.getName() + "'");
        }

        return null;
    }

    /**
     * Save the configuration
     *
     * @param object Root configuration
     */
    public void save(JsonObject object)
    {
        Gson gson = new Gson();

        try
        {
            if(this.configurationFile.exists())
            {
                this.configurationFile.delete();
                this.configurationFile.createNewFile();
            }

            FileWriter writer = new FileWriter(this.configurationFile);
            writer.write(gson.toJson(object));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}