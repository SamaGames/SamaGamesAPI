package net.samagames.api.i18n;

public enum ProjectNames
{
    /**
     * Global
     */

    PROXY(1),
    HUB(2),


    /**
     * Long games
     */

    QUAKE(3),
    UPPERVOID(4),
    DIMENSIONS(5),
    HEROBATTLE(6),
    CHUNKWARS(7),


    /**
     * Survival games
     */

    UHC(8),
    UHCRUN(9),
    DOUBLERUNNER(10),
    UHCRANDOM(11),
    RANDOMRUN(12),


    /**
     * Arcade games
     */

    AGARMC(13),
    HANGOVERGAMES(14),
    WITHERPARTY(15),
    BURNTHATCHICKEN(16),
    PACMAN(17),
    BOMBERMAN(18),
    FLYRING(19),
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
