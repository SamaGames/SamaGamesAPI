package net.samagames.tools.cameras;

import net.minecraft.server.v1_9_R2.EntityBat;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.minecraft.server.v1_9_R2.SoundEffect;
import net.minecraft.server.v1_9_R2.World;

public class EntityCamera extends EntityBat
{
    public EntityCamera(World world)
    {
        super(world);

        this.setInvisible(true);
        this.setInvulnerable(true);
        // TODO: this.setNoGravity(true);
        this.setAI(false);
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

    @Override
    protected SoundEffect G()
    {
        return null;
    }
}
