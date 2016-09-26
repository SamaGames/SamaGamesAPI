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
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class ProfileLoader {
    private final String uuid;
    private final String name;
    private final String skinOwner;

    public ProfileLoader(String uuid, String name) {
        this(uuid, name, name);
    }

    public ProfileLoader(String uuid, String name, String skinOwner) {
        this.uuid = uuid == null ? null : uuid.replaceAll("-", ""); //We add these later
        String displayName = ChatColor.translateAlternateColorCodes('&', name);
        this.name = ChatColor.stripColor(displayName);
        this.skinOwner = getUUID(skinOwner);
    }

    public ProfileLoader(String uuid, String name, UUID skinOwner) {
        this.uuid = uuid == null ? null : uuid.replaceAll("-", ""); //We add these later
        String displayName = ChatColor.translateAlternateColorCodes('&', name);
        this.name = ChatColor.stripColor(displayName);
        this.skinOwner = skinOwner.toString().replaceAll("-", "");
    }

    /**
     Need to be called async
     */
    public GameProfile loadProfile() {
        UUID id = uuid == null ? parseUUID(getUUID(name)) : parseUUID(uuid);
        GameProfile skinProfile = new GameProfile(UUID.randomUUID(), name);

        try(Jedis jedis = SamaGamesAPI.get().getBungeeResource()) {
            String json = jedis == null ? null : jedis.get("cacheSkin:" + uuid);
            GameProfile profile;
            if (json == null)
            {
                //Requete
                profile = MinecraftServer.getServer().ay().fillProfileProperties(new GameProfile(id, null), true);

                if (jedis != null && profile.getName() != null)//Don't save if didn't got data from mojang
                {
                    JsonArray jsonArray = new JsonArray();
                    for (Property property : profile.getProperties().values())
                    {
                        jsonArray.add(new Gson().toJsonTree(property));
                    }
                    jedis.set("cacheSkin:" + uuid, jsonArray.toString());
                    jedis.expire("cacheSkin:" + uuid, 172800);//2 jours
                }
                skinProfile.getProperties().putAll(profile.getProperties());
            }else
            {
                JsonArray parse = new JsonParser().parse(json).getAsJsonArray();
                for (JsonElement object : parse)
                {
                    Property property = new Gson().fromJson(object.toString(), Property.class);
                    skinProfile.getProperties().put(property.getName(), property);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return skinProfile;
    }

    @SuppressWarnings("deprecation")
    private String getUUID(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replaceAll("-", "");
    }

    private UUID parseUUID(String uuidStr) {
        // Split uuid in to 5 components
        String[] uuidComponents = new String[] { uuidStr.substring(0, 8),
                uuidStr.substring(8, 12), uuidStr.substring(12, 16),
                uuidStr.substring(16, 20),
                uuidStr.substring(20, uuidStr.length())
        };

        // Combine components with a dash
        StringBuilder builder = new StringBuilder();
        for (String component : uuidComponents) {
            builder.append(component).append('-');
        }

        // Correct uuid length, remove last dash
        builder.setLength(builder.length() - 1);
        return UUID.fromString(builder.toString());
    }
}