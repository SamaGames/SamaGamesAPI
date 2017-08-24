package net.samagames.tools.gameprofile;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import redis.clients.jedis.Jedis;

import java.util.UUID;

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
                profile = MinecraftServer.getServer().az().fillProfileProperties(new GameProfile(id, null), true);

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