package net.samagames.retrocompatibility;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.signs.SignData;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

@Deprecated
/**
 * @deprecated Use only for compatibility reasons. Will be removed when the API will be deployed on the whole network, as it won't be usefull anymore.
 */
public class JsonArena {

    public String bungeeName;
    private String map;
    private int maxPlayers;
    private int vipPlayers;
    private String status;
    private int players;
    private String gameType;

    public JsonArena() {
    }

    public static JsonArena fromSign(SignData sign) {
        return fromSign(sign, 3);
    }

    public static JsonArena fromSign(SignData sign, int vipPlayers) {
        JsonArena arena = new JsonArena();
        arena.bungeeName = sign.getBungeeName();
        arena.gameType = sign.getGameType();
        arena.map = sign.getMap();
        arena.status = (sign.isCanJoin()) ? "available" : "ingame";
        arena.maxPlayers = sign.getMaxPlayers() - vipPlayers;
        arena.vipPlayers = vipPlayers;
        arena.players = sign.getPlayers();
        return arena;
    }

    public void send() {
        SamaGamesAPI.get().getPubSub().send("arenasInfos", new Gson().toJson(this));
    }

    public JsonArena(String map, int maxPlayers, int vipPlayers, String status, int players, String bungeeName, String gameType) {
        this.map = map;
        this.maxPlayers = maxPlayers;
        this.vipPlayers = vipPlayers;
        this.status = status;
        this.players = players;
        this.bungeeName = bungeeName;
        this.gameType = gameType;
    }

}
