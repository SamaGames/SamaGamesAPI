package net.samagames.api.signs;

public class SignBuilder {

    private SignData sign;

    public SignBuilder(String bungeeName, String gameType) {
        this.sign = new SignData();
        sign.setBungeeName(bungeeName);
        sign.setGameType(gameType);
    }

    public SignBuilder setMap(String mapLine) {
        if (mapLine.length() > 16)
            throw new IllegalArgumentException("One sign line cannot have a lenght which is higher than 16.");
        else {
            sign.setMap(mapLine);
            return this;
        }
    }

    public SignBuilder setAllowJoin(boolean allowJoin) {
        sign.setCanJoin(allowJoin);
        return this;
    }

    public SignBuilder setSlots(int players, int max) {
        sign.setMaxPlayers(max);
        sign.setPlayers(max);
        return this;
    }

    public SignBuilder setStateLine(String stateLine) {
        if (stateLine.length() > 16)
            throw new IllegalArgumentException("One sign line cannot have a lenght which is higher than 16.");
        else {
            sign.setStateLine(stateLine);
            return this;
        }
    }

    public void send() {
        sign.send();
    }

}
