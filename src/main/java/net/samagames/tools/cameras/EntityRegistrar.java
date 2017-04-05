package net.samagames.tools.cameras;

import net.samagames.tools.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *                )\._.,--....,'``.
 * .b--.        /;   _.. \   _\  (`._ ,.
 * `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
 *
 * Created by Jérémy L. (BlueSlime) on 18/01/2017
 */
class EntityRegistrar
{
    private static Object[] BIOMES;

    private static Class<?> entityTypesClass;
    private static Class<?> entityInsentientClass;
    private static Class<?> biomeBaseClass;
    private static Class<?> biomeMetaClass;

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

        if (entityInsentientClass.isAssignableFrom(nmsClass) && entityInsentientClass.isAssignableFrom(customClass))
        {
            for (Object biomeBase : BIOMES)
            {
                if (biomeBase == null)
                    break;

                for (String field : new String[]{"u", "v", "w", "x"})
                {
                    try
                    {
                        Field list = biomeBaseClass.getDeclaredField(field);
                        list.setAccessible(true);
                        List<Object> mobList = (List<Object>) list.get(biomeBase);

                        Field entityClassField = biomeMetaClass.getDeclaredField("b");

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

    private static void registerEntityInEntityEnum(Class<?> paramClass, String paramString, int paramInt) throws Exception
    {
        ((Map<String, Class<?>>) getPrivateStatic(entityTypesClass, "c")).put(paramString, paramClass);
        ((Map<Class<?>, String>) getPrivateStatic(entityTypesClass, "d")).put(paramClass, paramString);
        ((Map<Integer, Class<?>>) getPrivateStatic(entityTypesClass, "e")).put(paramInt, paramClass);
        ((Map<Class<?>, Integer>) getPrivateStatic(entityTypesClass, "f")).put(paramClass, paramInt);
        ((Map<String, Integer>) getPrivateStatic(entityTypesClass, "g")).put(paramString, paramInt);
    }

    private static Object getPrivateStatic(Class clazz, String f) throws Exception
    {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);

        return field.get(null);
    }

    static
    {
        try
        {
            entityTypesClass = Reflection.getNMSClass("EntityTypes");
            entityInsentientClass = Reflection.getNMSClass("EntityInsentient");
            biomeBaseClass = Reflection.getNMSClass("BiomeBase");
            biomeMetaClass = Reflection.getNMSClass("BiomeBase$BiomeMeta");

            Class<?> minecraftKeyClass = Reflection.getNMSClass("MinecraftKey");
            Class<?> registryMaterialsClass = Reflection.getNMSClass("RegistryMaterials");
            Method keySetMethod = registryMaterialsClass.getMethod("keySet");
            Method getMethod = registryMaterialsClass.getMethod("get", Object.class);
            Method sizeMethod = keySetMethod.getReturnType().getMethod("size");
            Field registryField = biomeBaseClass.getField("REGISTRY_ID");

            Object registry = registryField.get(null);

            BIOMES = new Object[(int) sizeMethod.invoke(keySetMethod.invoke(registry))];

            int i = 0;

            for (Object key : (Set) keySetMethod.invoke(registry))
            {
                BIOMES[i] = getMethod.invoke(registry, key);
                i++;
            }
        }
        catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
