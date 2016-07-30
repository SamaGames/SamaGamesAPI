package net.samagames.api.games;

/**
 * ╱╲＿＿＿＿＿＿╱╲
 * ▏╭━━╮╭━━╮▕
 * ▏┃＿＿┃┃＿＿┃▕
 * ▏┃＿▉┃┃▉＿┃▕
 * ▏╰━━╯╰━━╯▕
 * ╲╰╰╯╲╱╰╯╯╱  Created by Silvanosky on 06/05/2016
 * ╱╰╯╰╯╰╯╰╯╲
 * ▏▕╰╯╰╯╰╯▏▕
 * ▏▕╯╰╯╰╯╰▏▕
 * ╲╱╲╯╰╯╰╱╲╱
 * ＿＿╱▕▔▔▏╲＿＿
 * ＿＿▔▔＿＿▔▔＿＿
 */
public enum GamesNames {
    GLOBAL(0),
    HEROBATTLE(1),
    JUKEBOX(2),
    QUAKE(3),
    UHC(4),
    UHCRUN(5),
    DOUBLERUNNER(6),
    UHCRANDOM(7),
    RANDOMRUN(8),
    ULTRAFLAGKEEPER(9),
    UPPERVOID(10),
    DIMENSIONS(11),
    BOWLING(12);

    private int value;

    GamesNames(int value)
    {
        this.value = value;
    }

    public int intValue()
    {
        return value;
    }
}
