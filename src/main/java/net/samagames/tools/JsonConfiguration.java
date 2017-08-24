package net.samagames.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.logging.Level;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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

            FileInputStream fileInputStream = new FileInputStream(this.configurationFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String currentLine;

            while((currentLine = br.readLine()) != null)
            {
                builder.append(currentLine);
            }

            br.close();
            inputStreamReader.close();
            fileInputStream.close();

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