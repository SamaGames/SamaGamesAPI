package net.samagames.core.api.player;

import java.util.HashMap;

/**
 * Created by LeadDev on 18/10/14.
 */
class Multiplier {

    public int globalAmount = 1;
    public HashMap<String, Integer> infos = new HashMap<>();

    public int getGlobalAmount()
    {
        return (globalAmount >= 1)? globalAmount : 1;
    }

}
