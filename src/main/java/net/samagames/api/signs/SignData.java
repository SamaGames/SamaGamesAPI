package net.samagames.api.signs;

import net.samagames.api.SamaGamesAPI;
import net.samagames.core.APIPlugin;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import com.google.gson.Gson;

public class SignData {

    private String bungeeName;
    private String gameType;
    private String map;
    private Integer players;
    private Integer maxPlayers;
    private boolean canJoin;
    private String stateLine;

    public SignData(String bungeeName, String gameType, String map, int players, int maxPlayers, String stateLine, boolean canJoin) {
        this.bungeeName = bungeeName;
        this.gameType = gameType;
        this.map = map;
        this.maxPlayers = maxPlayers;
        this.players = players;
        this.stateLine = stateLine;
        this.canJoin = canJoin;
    }

    public SignData() {

    }

    public String getBungeeName() {
        return bungeeName;
    }

    public void setBungeeName(String bungeeName) {
        this.bungeeName = bungeeName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Integer getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getStateLine() {
        return stateLine;
    }

    public void setStateLine(String stateLine) {
        this.stateLine = stateLine;
    }

    public boolean isCanJoin() {
        return canJoin;
    }

    public void setCanJoin(boolean canJoin) {
        this.canJoin = canJoin;
    }

	public void send() {
		if (getBungeeName() == null || getGameType() == null) {
			throw new IllegalStateException("Sign data is not complete");
		} else {
			SamaGamesAPI.get().getPubSub().send("lobbysChannel", getRawData());
		}
	}

    public void display(Sign sign) {
        if (bungeeName == null)
            sign.setLine(0, ChatColor.DARK_RED + "<Erreur>");
        else {
            String[] parts = bungeeName.split("_");
            sign.setLine(0, ChatColor.DARK_RED + StringUtils.join(parts, " "));
        }

        sign.setLine(1, ((map == null) ? "<map>" : map));

        String slots = "{PLAYERS}/{MAX}";
        if (getPlayers() != null)
            slots = slots.replace("{PLAYERS}", getPlayers() + "");
        if (getMaxPlayers() != null)
            slots = slots.replace("{MAX}", getMaxPlayers() + "");

        sign.setLine(2, slots);
        sign.setLine(3, (stateLine == null) ? ChatColor.RED + "<state>" : stateLine);

        // Make sure to run this sync
        Bukkit.getScheduler().runTask(APIPlugin.getInstance(), () -> sign.update());
    }

    public String getRawData() {
        return new Gson().toJson(this);
    }
}
