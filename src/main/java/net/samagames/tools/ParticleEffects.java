package net.samagames.tools;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public enum ParticleEffects {

    HUGE_EXPLOSION("hugeexplosion"),
    LARGE_EXPLODE("largeexplode"),
    FIREWORKS_SPARK("fireworksSpark"),
    BUBBLE("bubble"),
    SUSPEND("suspend"),
    DEPTH_SUSPEND("depthSuspend"),
    TOWN_AURA("townaura"),
    CRIT("crit"),
    MAGIC_CRIT("magicCrit"),
    MOB_SPELL("mobSpell"),
    MOB_SPELL_AMBIENT("mobSpellAmbient"),
    SPELL("spell"),
    INSTANT_SPELL("instantSpell"),
    WITCH_MAGIC("witchMagic"),
    NOTE("note"),
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"),
    EXPLODE("explode"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    SPLASH("splash"),
    LARGE_SMOKE("largesmoke"),
    CLOUD("cloud"),
    RED_DUST("reddust"),
    SNOWBALL_POOF("snowballpoof"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    SNOW_SHOVEL("snowshovel"),
    SLIME("slime"),
    HEART("heart"),
    ANGRY_VILLAGER("angryVillager"),
    HAPPY_VILLAGER("happerVillager"),
    ICONCRACK("iconcrack_"),
    ICONCRASH("iconcrack_0sqd"),
    TILECRACK("tilecrack_");

    private static Method getEntityHandle;
    private static Field getPlayerConnection;
    private static Method sendPacket;

    static {
        try {

            getEntityHandle = Reflection.getOBCClass("entity.CraftPlayer").getMethod("getHandle");
            getPlayerConnection = Reflection.getNMSClass("EntityPlayer").getDeclaredField("playerConnection");
            sendPacket = Reflection.getNMSClass("PlayerConnection").getMethod("sendPacket", Reflection.getNMSClass("Packet"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> nmsPacketPlayOutWorldParticles = Reflection.getNMSClass("PacketPlayOutWorldParticles");
    private String particleName;

    ParticleEffects(String particleName) {
        this.particleName = particleName;


    }

    //simple method for sending a packet to a CraftPlayer
    public static void sendPacket(Object packet, Player player) {
        try {
            Object nms_player = getEntityHandle.invoke(player);
            Object nms_connection = getPlayerConnection.get(nms_player);
            sendPacket.invoke(nms_connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
        Object packet = nmsPacketPlayOutWorldParticles.newInstance();
        ReflectionUtilities.setValue(packet, "a", particleName);
        ReflectionUtilities.setValue(packet, "b", (float) location.getX());
        ReflectionUtilities.setValue(packet, "c", (float) location.getY());
        ReflectionUtilities.setValue(packet, "d", (float) location.getZ());
        ReflectionUtilities.setValue(packet, "e", offsetX);
        ReflectionUtilities.setValue(packet, "f", offsetY);
        ReflectionUtilities.setValue(packet, "g", offsetZ);
        ReflectionUtilities.setValue(packet, "h", speed);
        ReflectionUtilities.setValue(packet, "i", count);
        sendPacket(packet, player);
    }

    public static class ReflectionUtilities {

        /**
         * sets a value of an {@link Object} via reflection
         *
         * @param instance  instance the class to use
         * @param fieldName the name of the {@link java.lang.reflect.Field} to modify
         * @param value     the value to set
         * @throws Exception
         */
        public static void setValue(Object instance, String fieldName, Object value) throws Exception {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        }

        /**
         * get a value of an {@link Object}'s {@link java.lang.reflect.Field}
         *
         * @param instance  the target {@link Object}
         * @param fieldName name of the {@link java.lang.reflect.Field}
         * @return the value of {@link Object} instance's {@link java.lang.reflect.Field} with the
         * name of fieldName
         * @throws Exception
         */
        public static Object getValue(Object instance, String fieldName) throws Exception {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        }

    }

}


