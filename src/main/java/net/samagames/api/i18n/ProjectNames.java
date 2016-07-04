package net.samagames.api.i18n;

public enum ProjectNames
{
    /**
     * Global
     */

    PROXY(0),
    HUB(1),


    /**
     * Long games
     */

    QUAKE(2),
    UPPERVOID(3),
    DIMENSIONS(4),
    HEROBATTLE(5),
    CHUNKWARS(6),


    /**
     * Survival games
     */

    UHC(7),
    UHCRUN(8),
    DOUBLERUNNER(9),
    UHCRANDOM(10),
    RANDOMRUN(11),


    /**
     * Arcade games
     */

    AGARMC(12),
    HANGOVERGAMES(13),
    WITHERPARTY(14),
    BURNTHATCHICKEN(15),
    PACMAN(16),
    BOMBERMAN(17),
    FLYRING(18),
    ;

    private int value;

    ProjectNames(int value)
    {
        this.value = value;
    }

    public int intValue()
    {
        return value;
    }
}
