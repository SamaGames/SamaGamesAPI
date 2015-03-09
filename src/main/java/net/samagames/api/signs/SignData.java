package net.samagames.api.signs;

public class SignData {

    private String bungeeName;
    private String gameType;
    private String map;
    private String slotsLine;
    private String stateLine;
    private boolean canJoin;

    public SignData(String bungeeName, String gameType, String map, String slotsLine, String stateLine, boolean canJoin) {
        this.bungeeName = bungeeName;
        this.gameType = gameType;
        this.map = map;
        this.slotsLine = slotsLine;
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

    public String getSlotsLine() {
        return slotsLine;
    }

    public void setSlotsLine(String slotsLine) {
        this.slotsLine = slotsLine;
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
}
