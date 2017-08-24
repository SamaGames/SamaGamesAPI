package net.samagames.api.games;

import net.samagames.tools.ColorUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class WinEffect implements Runnable
{
    private final Player player;
    private int time;

    public WinEffect(Player player)
    {
        this.player = player;
        this.time = 0;
    }

    @Override
    public void run()
    {
        if (this.time < 20)
        {
            Firework fw = (Firework) player.getWorld().spawnEntity(player.getPlayer().getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            Random r = new Random();
            int rt = r.nextInt(4) + 1;

            FireworkEffect.Type type = FireworkEffect.Type.BALL;

            if (rt == 1)
                type = FireworkEffect.Type.BALL;
            else if (rt == 2)
                type = FireworkEffect.Type.BALL_LARGE;
            else if (rt == 3)
                type = FireworkEffect.Type.BURST;
            else if (rt == 4)
                type = FireworkEffect.Type.CREEPER;
            else if (rt == 5)
                type = FireworkEffect.Type.STAR;

            int r1i = r.nextInt(15) + 1;
            int r2i = r.nextInt(15) + 1;

            Color c1 = ColorUtils.getColor(r1i);
            Color c2 = ColorUtils.getColor(r2i);

            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
            fwm.addEffect(effect);

            int rp = r.nextInt(2) + 1;
            fwm.setPower(rp);

            fw.setFireworkMeta(fwm);

            this.time++;
        }
    }
}
