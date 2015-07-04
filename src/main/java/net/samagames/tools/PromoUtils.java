package net.samagames.tools;

import net.samagames.api.SamaGamesAPI;
import redis.clients.jedis.Jedis;

public class PromoUtils
{
    public static Promo getCurrentPromo()
    {
        Jedis jedis = SamaGamesAPI.get().getResource();
        String prom = jedis.get("coins:currentpromo");
        jedis.close();

        if (prom == null)
            return null;
        else
            return new Promo(prom);
    }
}
