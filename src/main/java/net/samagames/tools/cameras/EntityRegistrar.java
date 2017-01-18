package net.samagames.tools.cameras;

import net.minecraft.server.v1_10_R1.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 18/01/2017
 */
class EntityRegistrar
{
    private static final BiomeBase[] BIOMES;

    static void registerEntity(String name, int id, Class<? extends Entity> nmsClass, Class<? extends Entity> customClass)
    {
        try
        {
            registerEntityInEntityEnum(customClass, name, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return;
        }

        if (EntityInsentient.class.isAssignableFrom(nmsClass) && EntityInsentient.class.isAssignableFrom(customClass))
        {
            for (BiomeBase biomeBase : BIOMES)
            {
                if (biomeBase == null)
                    break;

                for (String field : new String[]{"u", "v", "w", "x"})
                {
                    try
                    {
                        Field list = BiomeBase.class.getDeclaredField(field);
                        list.setAccessible(true);
                        List<BiomeBase.BiomeMeta> mobList = (List<BiomeBase.BiomeMeta>) list.get(biomeBase);

                        mobList.stream().filter(meta -> nmsClass.equals(meta.b)).forEach(meta -> meta.b = (Class<? extends EntityInsentient>) customClass);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void registerEntityInEntityEnum(Class<? extends Entity> paramClass, String paramString, int paramInt) throws Exception
    {
        ((Map<String, Class<? extends Entity>>) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
        ((Map<Class<? extends Entity>, String>) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
        ((Map<Integer, Class<? extends Entity>>) getPrivateStatic(EntityTypes.class, "e")).put(paramInt, paramClass);
        ((Map<Class<? extends Entity>, Integer>) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, paramInt);
        ((Map<String, Integer>) getPrivateStatic(EntityTypes.class, "g")).put(paramString, paramInt);
    }

    private static Object getPrivateStatic(Class clazz, String f) throws Exception
    {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);

        return field.get(null);
    }

    static
    {
        BIOMES = new BiomeBase[BiomeBase.REGISTRY_ID.keySet().size()];

        int i = 0;

        for (MinecraftKey key : BiomeBase.REGISTRY_ID.keySet())
        {
            BIOMES[i] = BiomeBase.REGISTRY_ID.get(key);
            i++;
        }
    }
}
