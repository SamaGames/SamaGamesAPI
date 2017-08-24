package net.samagames.tools.cameras;

import net.minecraft.server.v1_12_R1.*;

import java.lang.reflect.Field;
import java.util.List;

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
class EntityRegistrar
{
    private static BiomeBase[] BIOMES;

    static <E extends Entity> void registerEntity(String name, int id, Class<? extends E> nmsClass, Class<? extends E> customClass)
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

                for (String field : new String[]{"t", "u", "v", "w"})
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
        MinecraftKey key = new MinecraftKey(name);
        EntityTypes.b.a(id, key, customClass);

        if (!EntityTypes.d.contains(key))
            EntityTypes.d.add(key);
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
