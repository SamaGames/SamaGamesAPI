package net.samagames.tools.cameras;

import net.minecraft.server.v1_12_R1.*;

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
public class EntityCamera extends EntityBat
{
    public EntityCamera(World world)
    {
        super(world);

        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.setNoAI(true);
    }

    public EntityCamera(Object world)
    {
        this((World) world);
    }

    @Override
    protected void M() {}

    @Override
    public void b(NBTTagCompound nbttagcompound) {}

    @Override
    public boolean c(NBTTagCompound nbttagcompound)
    {
        return false;
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {}

    @Override
    public boolean d(NBTTagCompound nbttagcompound)
    {
        return false;
    }

    @Override
    public void f(NBTTagCompound nbttagcompound) {}

    public SoundEffect F()
    {
        return null;
    }

    protected SoundEffect d(DamageSource var1)
    {
        return null;
    }

    @Override
    protected SoundEffect cf()
    {
        return null;
    }
}
