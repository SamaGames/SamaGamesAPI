package net.samagames.api.signs;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

public class SignMaker {

    private SignData sign;

    public SignMaker(String bungeeName, String gameType) {
        this.sign = new SignData();
        sign.setBungeeName(bungeeName);
        sign.setGameType(gameType);
    }

    public SignMaker setMap(String mapLine) {
        if (mapLine.length() > 16)
            throw new IllegalArgumentException("One sign line cannot have a lenght which is higher than 16.");
        else {
            sign.setMap(mapLine);
            return this;
        }
    }

    public SignMaker setSlotsLine(String slotsLine) {
        if (slotsLine.length() > 16)
            throw new IllegalArgumentException("One sign line cannot have a lenght which is higher than 16.");
        else {
            sign.setSlotsLine(slotsLine);
            return this;
        }
    }

    public SignMaker setStateLine(String stateLine) {
        if (stateLine.length() > 16)
            throw new IllegalArgumentException("One sign line cannot have a lenght which is higher than 16.");
        else {
            sign.setStateLine(stateLine);
            return this;
        }
    }

    public void send() {
        if (sign.getBungeeName() == null || sign.getGameType() == null) {
            throw new IllegalStateException("Sign data is not complete");
        } else {
            String json = new Gson().toJson(sign);
            SamaGamesAPI.get().getPubSub().send("lobbysChannel", json);
        }
    }

}
