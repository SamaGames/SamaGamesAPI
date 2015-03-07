/**
 * Copyright © 2013 tuxed <write@imaginarycode.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package net.samagames.core.api.names;


import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class NameFetcher {
    private static JsonParser parser = new JsonParser();

    public static List<String> nameHistoryFromUuid(UUID uuid) {
        URLConnection connection;
        try {
            connection = new URL("https://api.mojang.com/user/profiles/"
                    + uuid.toString().replace("-", "").toLowerCase() + "/names"
            ).openConnection();
            String text = new Scanner(connection.getInputStream()).useDelimiter("\\Z").next();
            JsonArray list = (JsonArray) parser.parse(text);
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                names.add(((JsonObject) list.get(i)).get("name").getAsString());
            }
            return names;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}