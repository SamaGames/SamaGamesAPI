package net.samagames.tools.cameras;

import net.minecraft.server.v1_12_R1.*;

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
