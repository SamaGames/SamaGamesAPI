package net.samagames.tools.cameras;

import net.minecraft.server.v1_12_R1.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 18/01/2017
 */
class EntityRegistrar
{
    private static BiomeBase[] BIOMES;

    static void registerEntity(String name, int id, Class nmsClass, Class customClass)
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
            for (Object biomeBase : BIOMES)
            {
                if (biomeBase == null)
                    break;

                for (String field : new String[]{"u", "v", "w", "x"})
                {
                    try
                    {
                        Field list = BiomeBase.class.getDeclaredField(field);
                        list.setAccessible(true);
                        List<Object> mobList = (List<Object>) list.get(biomeBase);

                        Field entityClassField = BiomeBase.BiomeMeta.class.getDeclaredField("b");

                        for (Object mob : mobList)
                            if (nmsClass.getClass().equals(entityClassField.get(mob)))
                                entityClassField.set(mob, customClass);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void registerEntityInEntityEnum(Class<? extends Entity> customClass, String name, int id) throws Exception
    {
        MinecraftKey key = new MinecraftKey(name.toLowerCase());
        EntityTypes.b.a(id, key, customClass);
        EntityTypes.d.add(key);
        ((List<String>) getPrivateStatic(EntityTypes.class, "g")).set(id, name);
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
