package net.samagames.tools.gameprofile;

/**
 * ╱╲＿＿＿＿＿＿╱╲
 * ▏╭━━╮╭━━╮▕
 * ▏┃＿＿┃┃＿＿┃▕
 * ▏┃＿▉┃┃▉＿┃▕
 * ▏╰━━╯╰━━╯▕
 * ╲╰╰╯╲╱╰╯╯╱  Created by Silvanosky on 21/04/2016
 * ╱╰╯╰╯╰╯╰╯╲
 * ▏▕╰╯╰╯╰╯▏▕
 * ▏▕╯╰╯╰╯╰▏▕
 * ╲╱╲╯╰╯╰╱╲╱
 * ＿＿╱▕▔▔▏╲＿＿
 * ＿＿▔▔＿＿▔▔＿＿
 */

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.logging.Level;

public class ProfileLoader
{
    /**
        Need to be called async
     */
    public static GameProfile loadProfile(UUID profileOwner, String name, UUID skinOwner)
    {
        Bukkit.getLogger().info("Loading profile of " + skinOwner.toString() + " to load his skin...");

        UUID id = profileOwner == null ? UUID.randomUUID() : profileOwner;
        GameProfile profile = new GameProfile(id, name);

        Bukkit.getLogger().info("Adding properties...");

        addProperties(profile, skinOwner);

        return profile;
    }

    private static void addProperties(GameProfile profile, UUID skinOwner)
    {
        Jedis jedis = SamaGamesAPI.get().getBungeeResource();

        try
        {
            Collection<Property> textures = getSkinFromCache(jedis, skinOwner);

            if (textures == null)
            {
                Bukkit.getLogger().info("Fetching properties using MinecraftServer");

                GameProfile skinOwnerProfile = MinecraftServer.getServer().ay().fillProfileProperties(new GameProfile(skinOwner, null), false);
                textures = skinOwnerProfile.getProperties().get("textures");
                putSkinInCache(jedis, skinOwner, textures);
            }
            else
            {
                Bukkit.getLogger().info("Textures found in cache...");
            }

            Bukkit.getLogger().info("Properties: " + SERIALIZER.toJson(textures));

            if (profile.getProperties().containsKey("textures"))
                profile.getProperties().removeAll("textures");

            profile.getProperties().putAll("textures", textures);
        }
        catch (Exception ex)
        {
            Bukkit.getLogger().log(Level.SEVERE, "Can't load the skin of the owner " + skinOwner.toString(), ex);
        }
        finally
        {
            if (jedis != null)
                jedis.close();
        }
    }

    private static void putSkinInCache(Jedis jedis, UUID skinOwner, Collection<Property> properties)
    {
        if (jedis == null)
            return;

        String key = "cacheskin:" + skinOwner.toString();

        if (jedis.get(key) != null)
            jedis.del(key);

        jedis.set(key, SERIALIZER.toJson(properties));
        jedis.expire(key, 172800); // 2 days
    }

    private static Collection<Property> getSkinFromCache(Jedis jedis, UUID skinOwner)
    {
        if (jedis == null)
            return null;

        String key = "cacheskin:" + skinOwner.toString();

        if (jedis.get(key) == null)
            return null;

        return DESERIALIZER.fromJson(jedis.get(key), Collection.class);
    }

    public static final Gson SERIALIZER = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(Collection.class, (JsonSerializer<Collection<Property>>) (properties, type, jsonSerializationContext) ->
    {
        JsonArray jsonRoot = new JsonArray();

        properties.forEach(property ->
        {
            JsonObject jsonProperty = new JsonObject();
            jsonProperty.addProperty("name", property.getName());
            jsonProperty.addProperty("value", property.getValue());

            if (property.hasSignature())
                jsonProperty.addProperty("signature", property.getSignature());
        });

        return jsonRoot;
    }).create();

    public static final Gson DESERIALIZER = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(Collection.class, (JsonDeserializer<Collection<Property>>) (jsonElement, type, jsonDeserializationContext) ->
    {
        Collection<Property> properties = new ArrayList<>();

        for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++)
        {
            JsonObject jsonProperty = jsonElement.getAsJsonArray().get(i).getAsJsonObject();
            properties.add(new Property(jsonProperty.get("name").getAsString(), jsonProperty.get("value").getAsString(), (jsonProperty.has("signature") ? jsonProperty.get("signature").getAsString() : null)));
        }

        return properties;
    }).create();
}
