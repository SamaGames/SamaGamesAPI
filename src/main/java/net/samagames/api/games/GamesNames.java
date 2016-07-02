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
    UHCRUN(4),
    UPPERVOID(5),
    DIMENSION(6),
    BOWLING(7),
    CHUNKWARS(8),
    ;

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
