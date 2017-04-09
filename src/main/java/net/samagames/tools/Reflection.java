package net.samagames.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reflection {

    public static void playSound(Player player, Location location, String soundName, float volume, float pitch) {
        try {
            Class<?> soundClass = getClass("org.bukkit.Sound");
            Sound sound = (Sound) soundClass.getField(soundName).get(null);

            Method playSoundMethod = getMethod(player.getClass(), "playSound", Location.class, Sound.class, float.class, float.class);
            playSoundMethod.invoke(player, location, sound, volume, pitch);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String className) {
        try {
            return PackageType.MINECRAFT_SERVER.getClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getOBCClass(String className) {
        try {
            return PackageType.CRAFTBUKKIT.getClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Class<?> packetClass = getNMSClass("Packet");
            Class<?> entityPlayerClass = getNMSClass("EntityPlayer");
            Field playerConnectionField = getField(entityPlayerClass, "playerConnection");
            Method sendPacketMethod = getMethod(playerConnectionField.getType(), "sendPacket", packetClass);

            Object entityPlayer = getHandle(player);
            Object playerConnection = playerConnectionField.get(entityPlayer);

            sendPacketMethod.invoke(playerConnection, packet);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Method makeMethod(Class<?> clazz, String methodName, Class<?>... paramaters) {
        try {
            return clazz.getDeclaredMethod(methodName, paramaters);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T callMethod(Method method, Object instance, Object... paramaters) {
        if (method == null) throw new RuntimeException("No such method");
        method.setAccessible(true);
        try {
            return (T) method.invoke(instance, paramaters);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getCause());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> makeConstructor(Class<?> clazz, Class<?>... paramaterTypes) {
        try {
            return (Constructor<T>) clazz.getConstructor(paramaterTypes);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T callConstructor(Constructor<T> constructor, Object... paramaters) {
        if (constructor == null) throw new RuntimeException("No such constructor");
        constructor.setAccessible(true);
        try {
            return (T) constructor.newInstance(paramaters);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getCause());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Field makeField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getField(Field field, Object instance) {
        if (field == null) throw new RuntimeException("No such field");
        field.setAccessible(true);
        try {
            return (T) field.get(instance);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setField(Field field, Object instance, Object value) {
        if (field == null) throw new RuntimeException("No such field");
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public static <T> Class<? extends T> getClass(String name, Class<T> superClass) {
        try {
            return Class.forName(name).asSubclass(superClass);
        } catch (ClassCastException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static Object getHandle(Object obj) {
        try {
            return getMethod(obj.getClass(), "getHandle").invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean compareClassList(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (!Objects.equals(l1[i], l2[i])) {
                equal = false;
                break;
            }
        return equal;
    }

    /**
     * Returns the constructor of a given class with the given parameter types
     *
     * @param clazz          Target class
     * @param parameterTypes Parameter types of the desired constructor
     * @return The constructor of the target class with the specified parameter types
     * @throws NoSuchMethodException If the desired constructor with the specified parameter types cannot be found
     * @see DataType
     * @see DataType#getPrimitive(Class[])
     * @see DataType#compare(Class[], Class[])
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (!DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
                continue;
            }
            return constructor;
        }
        throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
    }

    /**
     * Returns the constructor of a desired class with the given parameter types
     *
     * @param className      Name of the desired target class
     * @param packageType    Package where the desired target class is located
     * @param parameterTypes Parameter types of the desired constructor
     * @return The constructor of the desired target class with the specified parameter types
     * @throws NoSuchMethodException  If the desired constructor with the specified parameter types cannot be found
     * @throws ClassNotFoundException ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see #getConstructor(Class, Class...)
     */
    public static Constructor<?> getConstructor(String className, PackageType packageType, Class<?>... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getConstructor(packageType.getClass(className), parameterTypes);
    }

    /**
     * Returns an instance of a class with the given arguments
     *
     * @param clazz     Target class
     * @param arguments Arguments which are used to construct an object of the target class
     * @return The instance of the target class with the specified arguments
     * @throws InstantiationException    If you cannot create an instance of the target class due to certain circumstances
     * @throws IllegalAccessException    If the desired constructor cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException  If the types of the arguments do not match the parameter types of the constructor (this should not occur since it searches for a constructor with the types of the arguments)
     * @throws InvocationTargetException If the desired constructor cannot be invoked
     * @throws NoSuchMethodException     If the desired constructor with the specified arguments cannot be found
     */
    public static Object instantiateObject(Class<?> clazz, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getConstructor(clazz, DataType.getPrimitive(arguments)).newInstance(arguments);
    }

    /**
     * Returns an instance of a desired class with the given arguments
     *
     * @param className   Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param arguments   Arguments which are used to construct an object of the desired target class
     * @return The instance of the desired target class with the specified arguments
     * @throws InstantiationException    If you cannot create an instance of the desired target class due to certain circumstances
     * @throws IllegalAccessException    If the desired constructor cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException  If the types of the arguments do not match the parameter types of the constructor (this should not occur since it searches for a constructor with the types of the arguments)
     * @throws InvocationTargetException If the desired constructor cannot be invoked
     * @throws NoSuchMethodException     If the desired constructor with the specified arguments cannot be found
     * @throws ClassNotFoundException    If the desired target class with the specified name and package cannot be found
     * @see #instantiateObject(Class, Object...)
     */
    public static Object instantiateObject(String className, PackageType packageType, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return instantiateObject(packageType.getClass(className), arguments);
    }

    /**
     * Returns a method of a class with the given parameter types
     *
     * @param clazz          Target class
     * @param methodName     Name of the desired method
     * @param parameterTypes Parameter types of the desired method
     * @return The method of the target class with the specified name and parameter types
     * @throws NoSuchMethodException If the desired method of the target class with the specified name and parameter types cannot be found
     * @see DataType#getPrimitive(Class[])
     * @see DataType#compare(Class[], Class[])
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(methodName) || !DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
                continue;
            }
            return method;
        }
        throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
    }

    /**
     * Returns a method of a desired class with the given parameter types
     *
     * @param className      Name of the desired target class
     * @param packageType    Package where the desired target class is located
     * @param methodName     Name of the desired method
     * @param parameterTypes Parameter types of the desired method
     * @return The method of the desired target class with the specified name and parameter types
     * @throws NoSuchMethodException  If the desired method of the desired target class with the specified name and parameter types cannot be found
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see #getMethod(Class, String, Class...)
     */
    public static Method getMethod(String className, PackageType packageType, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(packageType.getClass(className), methodName, parameterTypes);
    }

    /**
     * Invokes a method on an object with the given arguments
     *
     * @param instance   Target object
     * @param methodName Name of the desired method
     * @param arguments  Arguments which are used to invoke the desired method
     * @return The result of invoking the desired method on the target object
     * @throws IllegalAccessException    If the desired method cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException  If the types of the arguments do not match the parameter types of the method (this should not occur since it searches for a method with the types of the arguments)
     * @throws InvocationTargetException If the desired method cannot be invoked on the target object
     * @throws NoSuchMethodException     If the desired method of the class of the target object with the specified name and arguments cannot be found
     * @see #getMethod(Class, String, Class...)
     * @see DataType#getPrimitive(Object[])
     */
    public static Object invokeMethod(Object instance, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(instance.getClass(), methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    /**
     * Invokes a method of the target class on an object with the given arguments
     *
     * @param instance   Target object
     * @param clazz      Target class
     * @param methodName Name of the desired method
     * @param arguments  Arguments which are used to invoke the desired method
     * @return The result of invoking the desired method on the target object
     * @throws IllegalAccessException    If the desired method cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException  If the types of the arguments do not match the parameter types of the method (this should not occur since it searches for a method with the types of the arguments)
     * @throws InvocationTargetException If the desired method cannot be invoked on the target object
     * @throws NoSuchMethodException     If the desired method of the target class with the specified name and arguments cannot be found
     * @see #getMethod(Class, String, Class...)
     * @see DataType#getPrimitive(Object[])
     */
    public static Object invokeMethod(Object instance, Class<?> clazz, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(clazz, methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    /**
     * Invokes a method of a desired class on an object with the given arguments
     *
     * @param instance    Target object
     * @param className   Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param methodName  Name of the desired method
     * @param arguments   Arguments which are used to invoke the desired method
     * @return The result of invoking the desired method on the target object
     * @throws IllegalAccessException    If the desired method cannot be accessed due to certain circumstances
     * @throws IllegalArgumentException  If the types of the arguments do not match the parameter types of the method (this should not occur since it searches for a method with the types of the arguments)
     * @throws InvocationTargetException If the desired method cannot be invoked on the target object
     * @throws NoSuchMethodException     If the desired method of the desired target class with the specified name and arguments cannot be found
     * @throws ClassNotFoundException    If the desired target class with the specified name and package cannot be found
     * @see #invokeMethod(Object, Class, String, Object...)
     */
    public static Object invokeMethod(Object instance, String className, PackageType packageType, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
    }

    /**
     * Returns a field of the target class with the given name
     *
     * @param clazz     Target class
     * @param declared  Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The field of the target class with the specified name
     * @throws NoSuchFieldException If the desired field of the given class cannot be found
     * @throws SecurityException    If the desired field cannot be made accessible
     */
    public static Field getField(Class<?> clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * Returns a field of a desired class with the given name
     *
     * @param className   Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param declared    Whether the desired field is declared or not
     * @param fieldName   Name of the desired field
     * @return The field of the desired target class with the specified name
     * @throws NoSuchFieldException   If the desired field of the desired class cannot be found
     * @throws SecurityException      If the desired field cannot be made accessible
     * @throws ClassNotFoundException If the desired target class with the specified name and package cannot be found
     * @see #getField(Class, boolean, String)
     */
    public static Field getField(String className, PackageType packageType, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getField(packageType.getClass(className), declared, fieldName);
    }

    /**
     * Returns the value of a field of the given class of an object
     *
     * @param instance  Target object
     * @param clazz     Target class
     * @param declared  Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The value of field of the target object
     * @throws IllegalArgumentException If the target object does not feature the desired field
     * @throws IllegalAccessException   If the desired field cannot be accessed
     * @throws NoSuchFieldException     If the desired field of the target class cannot be found
     * @throws SecurityException        If the desired field cannot be made accessible
     * @see #getField(Class, boolean, String)
     */
    public static Object getValue(Object instance, Class<?> clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getField(clazz, declared, fieldName).get(instance);
    }

    /**
     * Returns the value of a field of a desired class of an object
     *
     * @param instance    Target object
     * @param className   Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param declared    Whether the desired field is declared or not
     * @param fieldName   Name of the desired field
     * @return The value of field of the target object
     * @throws IllegalArgumentException If the target object does not feature the desired field
     * @throws IllegalAccessException   If the desired field cannot be accessed
     * @throws NoSuchFieldException     If the desired field of the desired class cannot be found
     * @throws SecurityException        If the desired field cannot be made accessible
     * @throws ClassNotFoundException   If the desired target class with the specified name and package cannot be found
     * @see #getValue(Object, Class, boolean, String)
     */
    public static Object getValue(Object instance, String className, PackageType packageType, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getValue(instance, packageType.getClass(className), declared, fieldName);
    }

    /**
     * Returns the value of a field with the given name of an object
     *
     * @param instance  Target object
     * @param declared  Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @return The value of field of the target object
     * @throws IllegalArgumentException If the target object does not feature the desired field (should not occur since it searches for a field with the given name in the class of the object)
     * @throws IllegalAccessException   If the desired field cannot be accessed
     * @throws NoSuchFieldException     If the desired field of the target object cannot be found
     * @throws SecurityException        If the desired field cannot be made accessible
     * @see #getValue(Object, Class, boolean, String)
     */
    public static Object getValue(Object instance, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getValue(instance, instance.getClass(), declared, fieldName);
    }

    /**
     * Sets the value of a field of the given class of an object
     *
     * @param instance  Target object
     * @param clazz     Target class
     * @param declared  Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @param value     New value
     * @throws IllegalArgumentException If the type of the value does not match the type of the desired field
     * @throws IllegalAccessException   If the desired field cannot be accessed
     * @throws NoSuchFieldException     If the desired field of the target class cannot be found
     * @throws SecurityException        If the desired field cannot be made accessible
     * @see #getField(Class, boolean, String)
     */
    public static void setValue(Object instance, Class<?> clazz, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        getField(clazz, declared, fieldName).set(instance, value);
    }

    /**
     * Sets the value of a field of a desired class of an object
     *
     * @param instance    Target object
     * @param className   Name of the desired target class
     * @param packageType Package where the desired target class is located
     * @param declared    Whether the desired field is declared or not
     * @param fieldName   Name of the desired field
     * @param value       New value
     * @throws IllegalArgumentException If the type of the value does not match the type of the desired field
     * @throws IllegalAccessException   If the desired field cannot be accessed
     * @throws NoSuchFieldException     If the desired field of the desired class cannot be found
     * @throws SecurityException        If the desired field cannot be made accessible
     * @throws ClassNotFoundException   If the desired target class with the specified name and package cannot be found
     * @see #setValue(Object, Class, boolean, String, Object)
     */
    public static void setValue(Object instance, String className, PackageType packageType, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        setValue(instance, packageType.getClass(className), declared, fieldName, value);
    }

    /**
     * Sets the value of a field with the given name of an object
     *
     * @param instance  Target object
     * @param declared  Whether the desired field is declared or not
     * @param fieldName Name of the desired field
     * @param value     New value
     * @throws IllegalArgumentException If the type of the value does not match the type of the desired field
     * @throws IllegalAccessException   If the desired field cannot be accessed
     * @throws NoSuchFieldException     If the desired field of the target object cannot be found
     * @throws SecurityException        If the desired field cannot be made accessible
     * @see #setValue(Object, Class, boolean, String, Object)
     */
    public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setValue(instance, instance.getClass(), declared, fieldName, value);
    }

    /**
     * Sets a value of an {@link Object} via reflection
     *
     * @param instance  instance the class to use
     * @param fieldName the name of the {@link java.lang.reflect.Field} to modify
     * @param value     the value to set
     * @throws NoSuchFieldException, IllegalAccessException
     */
    public static void setValue(Object instance, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        setValue(instance, true, fieldName, value);
    }

    /**
     * Get a value of an {@link Object}'s {@link java.lang.reflect.Field}
     *
     * @param instance  the target {@link Object}
     * @param fieldName name of the {@link java.lang.reflect.Field}
     * @return the value of {@link Object} instance's {@link java.lang.reflect.Field} with the
     * name of fieldName
     * @throws NoSuchFieldException, IllegalAccessException
     */
    public static Object getValue(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getValue(instance, true, fieldName);
    }

    /**
     * Set a final static var
     * @param field The field object
     * @param value The new value
     * @throws ReflectiveOperationException
     */
    public static void setFinalStatic(Field field, Object value) throws ReflectiveOperationException {
        field.setAccessible(true);
        Class.class.getModifiers();
        Field mf = Field.class.getDeclaredField("modifiers");
        mf.setAccessible(true);
        mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }

    /**
     * Set a final static var
     * @param field The field object
     * @param value The new value
     * @throws ReflectiveOperationException
     */
    public static void setFinal(Object object, Field field, Object value) throws ReflectiveOperationException {
        field.setAccessible(true);
        Field mf = Field.class.getDeclaredField("modifiers");
        mf.setAccessible(true);
        mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(object, value);
    }

    /**
     * Represents an enumeration of dynamic packages of NMS and CraftBukkit
     * <p/>
     * This class is part of the <b>ReflectionUtils</b> and follows the same usage conditions
     *
     * @author DarkBlade12
     * @since 1.0
     */
    public enum PackageType {
        MINECRAFT_SERVER("net.minecraft.server." + getServerVersion()),
        CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion()),
        CRAFTBUKKIT_BLOCK(CRAFTBUKKIT, "block"),
        CRAFTBUKKIT_CHUNKIO(CRAFTBUKKIT, "chunkio"),
        CRAFTBUKKIT_COMMAND(CRAFTBUKKIT, "command"),
        CRAFTBUKKIT_CONVERSATIONS(CRAFTBUKKIT, "conversations"),
        CRAFTBUKKIT_ENCHANTMENS(CRAFTBUKKIT, "enchantments"),
        CRAFTBUKKIT_ENTITY(CRAFTBUKKIT, "entity"),
        CRAFTBUKKIT_EVENT(CRAFTBUKKIT, "event"),
        CRAFTBUKKIT_GENERATOR(CRAFTBUKKIT, "generator"),
        CRAFTBUKKIT_HELP(CRAFTBUKKIT, "help"),
        CRAFTBUKKIT_INVENTORY(CRAFTBUKKIT, "inventory"),
        CRAFTBUKKIT_MAP(CRAFTBUKKIT, "map"),
        CRAFTBUKKIT_METADATA(CRAFTBUKKIT, "metadata"),
        CRAFTBUKKIT_POTION(CRAFTBUKKIT, "potion"),
        CRAFTBUKKIT_PROJECTILES(CRAFTBUKKIT, "projectiles"),
        CRAFTBUKKIT_SCHEDULER(CRAFTBUKKIT, "scheduler"),
        CRAFTBUKKIT_SCOREBOARD(CRAFTBUKKIT, "scoreboard"),
        CRAFTBUKKIT_UPDATER(CRAFTBUKKIT, "updater"),
        CRAFTBUKKIT_UTIL(CRAFTBUKKIT, "util"),
        BUKKIT("org.bukkit");

        private final String path;

        /**
         * Construct a new package type
         *
         * @param path Path of the package
         */
        private PackageType(String path) {
            this.path = path;
        }

        /**
         * Construct a new package type
         *
         * @param parent Parent package of the package
         * @param path   Path of the package
         */
        private PackageType(PackageType parent, String path) {
            this(parent + "." + path);
        }

        /**
         * Returns the version of your server
         *
         * @return The server version
         */
        public static String getServerVersion() {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            return name.substring(name.lastIndexOf('.') + 1);
        }

        /**
         * Returns the path of this package type
         *
         * @return The path
         */
        public String getPath() {
            return path;
        }

        /**
         * Returns the class with the given name
         *
         * @param className Name of the desired class
         * @return The class with the specified name
         * @throws ClassNotFoundException If the desired class with the specified name and package cannot be found
         */
        public Class<?> getClass(String className) throws ClassNotFoundException {
            return Class.forName(this + "." + className);
        }

        // Override for convenience
        @Override
        public String toString() {
            return path;
        }
    }

    /**
     * Represents an enumeration of Java data types with corresponding classes
     * <p/>
     * This class is part of the <b>ReflectionUtils</b> and follows the same usage conditions
     *
     * @author DarkBlade12
     * @since 1.0
     */
    public enum DataType {
        BYTE(byte.class, Byte.class),
        SHORT(short.class, Short.class),
        INTEGER(int.class, Integer.class),
        LONG(long.class, Long.class),
        CHARACTER(char.class, Character.class),
        FLOAT(float.class, Float.class),
        DOUBLE(double.class, Double.class),
        BOOLEAN(boolean.class, Boolean.class);

        private static final Map<Class<?>, DataType> CLASS_MAP = new HashMap<>();
        private final Class<?> primitive;
        private final Class<?> reference;

        // Initialize map for quick class lookup
        static {
            for (DataType type : values()) {
                CLASS_MAP.put(type.primitive, type);
                CLASS_MAP.put(type.reference, type);
            }
        }

        /**
         * Construct a new data type
         *
         * @param primitive Primitive class of this data type
         * @param reference Reference class of this data type
         */
        private DataType(Class<?> primitive, Class<?> reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        /**
         * Returns the data type with the given primitive/reference class
         *
         * @param clazz Primitive/Reference class of the data type
         * @return The data type
         */
        public static DataType fromClass(Class<?> clazz) {
            return CLASS_MAP.get(clazz);
        }

        /**
         * Returns the primitive class of the data type with the given reference class
         *
         * @param clazz Reference class of the data type
         * @return The primitive class
         */
        public static Class<?> getPrimitive(Class<?> clazz) {
            DataType type = fromClass(clazz);
            return type == null ? clazz : type.getPrimitive();
        }

        /**
         * Returns the reference class of the data type with the given primitive class
         *
         * @param clazz Primitive class of the data type
         * @return The reference class
         */
        public static Class<?> getReference(Class<?> clazz) {
            DataType type = fromClass(clazz);
            return type == null ? clazz : type.getReference();
        }

        /**
         * Returns the primitive class array of the given class array
         *
         * @param classes Given class array
         * @return The primitive class array
         */
        public static Class<?>[] getPrimitive(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; index++) {
                types[index] = getPrimitive(classes[index]);
            }
            return types;
        }

        /**
         * Returns the reference class array of the given class array
         *
         * @param classes Given class array
         * @return The reference class array
         */
        public static Class<?>[] getReference(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; index++) {
                types[index] = getReference(classes[index]);
            }
            return types;
        }

        /**
         * Returns the primitive class array of the given object array
         *
         * @param objects Given object array
         * @return The primitive class array
         */
        public static Class<?>[] getPrimitive(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; index++) {
                types[index] = getPrimitive(objects[index].getClass());
            }
            return types;
        }

        /**
         * Returns the reference class array of the given object array
         *
         * @param objects Given object array
         * @return The reference class array
         */
        public static Class<?>[] getReference(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class<?>[length];
            for (int index = 0; index < length; index++) {
                types[index] = getReference(objects[index].getClass());
            }
            return types;
        }

        /**
         * Compares two class arrays on equivalence
         *
         * @param primary   Primary class array
         * @param secondary Class array which is compared to the primary array
         * @return Whether these arrays are equal or not
         */
        public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
            if (primary == null || secondary == null || primary.length != secondary.length) {
                return false;
            }
            for (int index = 0; index < primary.length; index++) {
                Class<?> primaryClass = primary[index];
                Class<?> secondaryClass = secondary[index];
                if (primaryClass.equals(secondaryClass) || primaryClass.isAssignableFrom(secondaryClass)) {
                    continue;
                }
                return false;
            }
            return true;
        }

        /**
         * Returns the primitive class of this data type
         *
         * @return The primitive class
         */
        public Class<?> getPrimitive() {
            return primitive;
        }

        /**
         * Returns the reference class of this data type
         *
         * @return The reference class
         */
        public Class<?> getReference() {
            return reference;
        }
    }

    /**
     * Represents an enumeration of all packet types that are featured in <b>Minecraft 1.7.10</b>
     * <p/>
     * If this enumeration is no longer up-to-date, please let me know in my <a href="http://forums.bukkit.org/threads/lib-1-7-particleeffect-v1-4.154406">forum post</a>
     * <p/>
     * This class is part of the <b>ReflectionUtils</b> and follows the same usage conditions
     *
     * @author DarkBlade12
     * @since 1.0
     */
    public enum PacketType {
        HANDSHAKING_IN_SET_PROTOCOL("PacketHandshakingInSetProtocol"),
        LOGIN_IN_ENCRYPTION_BEGIN("PacketLoginInEncryptionBegin"),
        LOGIN_IN_START("PacketLoginInStart"),
        LOGIN_OUT_DISCONNECT("PacketLoginOutDisconnect"),
        LOGIN_OUT_ENCRYPTION_BEGIN("PacketLoginOutEncryptionBegin"),
        LOGIN_OUT_SUCCESS("PacketLoginOutSuccess"),
        PLAY_IN_ABILITIES("PacketPlayInAbilities"),
        PLAY_IN_ARM_ANIMATION("PacketPlayInArmAnimation"),
        PLAY_IN_BLOCK_DIG("PacketPlayInBlockDig"),
        PLAY_IN_BLOCK_PLACE("PacketPlayInBlockPlace"),
        PLAY_IN_CHAT("PacketPlayInChat"),
        PLAY_IN_CLIENT_COMMAND("PacketPlayInClientCommand"),
        PLAY_IN_CLOSE_WINDOW("PacketPlayInCloseWindow"),
        PLAY_IN_CUSTOM_PAYLOAD("PacketPlayInCustomPayload"),
        PLAY_IN_ENCHANT_ITEM("PacketPlayInEnchantItem"),
        PLAY_IN_ENTITY_ACTION("PacketPlayInEntityAction"),
        PLAY_IN_FLYING("PacketPlayInFlying"),
        PLAY_IN_HELD_ITEM_SLOT("PacketPlayInHeldItemSlot"),
        PLAY_IN_KEEP_ALIVE("PacketPlayInKeepAlive"),
        PLAY_IN_LOOK("PacketPlayInLook"),
        PLAY_IN_POSITION("PacketPlayInPosition"),
        PLAY_IN_POSITION_LOOK("PacketPlayInPositionLook"),
        PLAY_IN_SET_CREATIVE_SLOT("PacketPlayInSetCreativeSlot "),
        PLAY_IN_SETTINGS("PacketPlayInSettings"),
        PLAY_IN_STEER_VEHICLE("PacketPlayInSteerVehicle"),
        PLAY_IN_TAB_COMPLETE("PacketPlayInTabComplete"),
        PLAY_IN_TRANSACTION("PacketPlayInTransaction"),
        PLAY_IN_UPDATE_SIGN("PacketPlayInUpdateSign"),
        PLAY_IN_USE_ENTITY("PacketPlayInUseEntity"),
        PLAY_IN_WINDOW_CLICK("PacketPlayInWindowClick"),
        PLAY_OUT_ABILITIES("PacketPlayOutAbilities"),
        PLAY_OUT_ANIMATION("PacketPlayOutAnimation"),
        PLAY_OUT_ATTACH_ENTITY("PacketPlayOutAttachEntity"),
        PLAY_OUT_BED("PacketPlayOutBed"),
        PLAY_OUT_BLOCK_ACTION("PacketPlayOutBlockAction"),
        PLAY_OUT_BLOCK_BREAK_ANIMATION("PacketPlayOutBlockBreakAnimation"),
        PLAY_OUT_BLOCK_CHANGE("PacketPlayOutBlockChange"),
        PLAY_OUT_CHAT("PacketPlayOutChat"),
        PLAY_OUT_CLOSE_WINDOW("PacketPlayOutCloseWindow"),
        PLAY_OUT_COLLECT("PacketPlayOutCollect"),
        PLAY_OUT_CRAFT_PROGRESS_BAR("PacketPlayOutCraftProgressBar"),
        PLAY_OUT_CUSTOM_PAYLOAD("PacketPlayOutCustomPayload"),
        PLAY_OUT_ENTITY("PacketPlayOutEntity"),
        PLAY_OUT_ENTITY_DESTROY("PacketPlayOutEntityDestroy"),
        PLAY_OUT_ENTITY_EFFECT("PacketPlayOutEntityEffect"),
        PLAY_OUT_ENTITY_EQUIPMENT("PacketPlayOutEntityEquipment"),
        PLAY_OUT_ENTITY_HEAD_ROTATION("PacketPlayOutEntityHeadRotation"),
        PLAY_OUT_ENTITY_LOOK("PacketPlayOutEntityLook"),
        PLAY_OUT_ENTITY_METADATA("PacketPlayOutEntityMetadata"),
        PLAY_OUT_ENTITY_STATUS("PacketPlayOutEntityStatus"),
        PLAY_OUT_ENTITY_TELEPORT("PacketPlayOutEntityTeleport"),
        PLAY_OUT_ENTITY_VELOCITY("PacketPlayOutEntityVelocity"),
        PLAY_OUT_EXPERIENCE("PacketPlayOutExperience"),
        PLAY_OUT_EXPLOSION("PacketPlayOutExplosion"),
        PLAY_OUT_GAME_STATE_CHANGE("PacketPlayOutGameStateChange"),
        PLAY_OUT_HELD_ITEM_SLOT("PacketPlayOutHeldItemSlot"),
        PLAY_OUT_KEEP_ALIVE("PacketPlayOutKeepAlive"),
        PLAY_OUT_KICK_DISCONNECT("PacketPlayOutKickDisconnect"),
        PLAY_OUT_LOGIN("PacketPlayOutLogin"),
        PLAY_OUT_MAP("PacketPlayOutMap"),
        PLAY_OUT_MAP_CHUNK("PacketPlayOutMapChunk"),
        PLAY_OUT_MAP_CHUNK_BULK("PacketPlayOutMapChunkBulk"),
        PLAY_OUT_MULTI_BLOCK_CHANGE("PacketPlayOutMultiBlockChange"),
        PLAY_OUT_NAMED_ENTITY_SPAWN("PacketPlayOutNamedEntitySpawn"),
        PLAY_OUT_NAMED_SOUND_EFFECT("PacketPlayOutNamedSoundEffect"),
        PLAY_OUT_OPEN_SIGN_EDITOR("PacketPlayOutOpenSignEditor"),
        PLAY_OUT_OPEN_WINDOW("PacketPlayOutOpenWindow"),
        PLAY_OUT_PLAYER_INFO("PacketPlayOutPlayerInfo"),
        PLAY_OUT_POSITION("PacketPlayOutPosition"),
        PLAY_OUT_REL_ENTITY_MOVE("PacketPlayOutRelEntityMove"),
        PLAY_OUT_REL_ENTITY_MOVE_LOOK("PacketPlayOutRelEntityMoveLook"),
        PLAY_OUT_REMOVE_ENTITY_EFFECT("PacketPlayOutRemoveEntityEffect"),
        PLAY_OUT_RESPAWN("PacketPlayOutRespawn"),
        PLAY_OUT_SCOREBOARD_DISPLAY_OBJECTIVE("PacketPlayOutScoreboardDisplayObjective"),
        PLAY_OUT_SCOREBOARD_OBJECTIVE("PacketPlayOutScoreboardObjective"),
        PLAY_OUT_SCOREBOARD_SCORE("PacketPlayOutScoreboardScore"),
        PLAY_OUT_SCOREBOARD_TEAM("PacketPlayOutScoreboardTeam"),
        PLAY_OUT_SET_SLOT("PacketPlayOutSetSlot"),
        PLAY_OUT_SPAWN_ENTITY("PacketPlayOutSpawnEntity"),
        PLAY_OUT_SPAWN_ENTITY_EXPERIENCE_ORB("PacketPlayOutSpawnEntityExperienceOrb"),
        PLAY_OUT_SPAWN_ENTITY_LIVING("PacketPlayOutSpawnEntityLiving"),
        PLAY_OUT_SPAWN_ENTITY_PAINTING("PacketPlayOutSpawnEntityPainting"),
        PLAY_OUT_SPAWN_ENTITY_WEATHER("PacketPlayOutSpawnEntityWeather"),
        PLAY_OUT_SPAWN_POSITION("PacketPlayOutSpawnPosition"),
        PLAY_OUT_STATISTIC("PacketPlayOutStatistic"),
        PLAY_OUT_TAB_COMPLETE("PacketPlayOutTabComplete"),
        PLAY_OUT_TILE_ENTITY_DATA("PacketPlayOutTileEntityData"),
        PLAY_OUT_TRANSACTION("PacketPlayOutTransaction"),
        PLAY_OUT_UPDATE_ATTRIBUTES("PacketPlayOutUpdateAttributes"),
        PLAY_OUT_UPDATE_HEALTH("PacketPlayOutUpdateHealth"),
        PLAY_OUT_UPDATE_SIGN("PacketPlayOutUpdateSign"),
        PLAY_OUT_UPDATE_TIME("PacketPlayOutUpdateTime"),
        PLAY_OUT_WINDOW_ITEMS("PacketPlayOutWindowItems"),
        PLAY_OUT_WORLD_EVENT("PacketPlayOutWorldEvent"),
        PLAY_OUT_WORLD_PARTICLES("PacketPlayOutWorldParticles"),
        STATUS_IN_PING("PacketStatusInPing"),
        STATUS_IN_START("PacketStatusInStart"),
        STATUS_OUT_PONG("PacketStatusOutPong"),
        STATUS_OUT_SERVER_INFO("PacketStatusOutServerInfo");

        private static final Map<String, PacketType> NAME_MAP = new HashMap<>();
        private final String name;
        private Class<?> packet;

        // Initialize map for quick name lookup
        static {
            for (PacketType type : values()) {
                NAME_MAP.put(type.name, type);
            }
        }

        /**
         * Construct a new packet type
         *
         * @param name Name of this packet
         */
        private PacketType(String name) {
            this.name = name;
        }

        /**
         * Returns the name of this packet type
         *
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the class of this packet
         *
         * @return The packet class
         * @throws ClassNotFoundException If the packet class cannot be found (the name differs in your Bukkit version)
         */
        public Class<?> getPacket() throws ClassNotFoundException {
            return packet == null ? (packet = PackageType.MINECRAFT_SERVER.getClass(name)) : packet;
        }
    }

}
