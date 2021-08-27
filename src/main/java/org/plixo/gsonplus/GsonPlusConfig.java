package org.plixo.gsonplus;

import com.google.gson.JsonElement;

import java.io.File;
import java.util.HashMap;

/**
 * Used for mapping for the primitives via {@link IObjectValue}
 * and {@link IDefaultObject} for default instances, when empty constructors in the Class doesn't fit.
 *
 * @author Plixo
 */
public class GsonPlusConfig {

    /**
     * Used in {@link GsonPlusBuilder} and {@link GsonPlus}
     */
    private static final HashMap<Class<?>, IObjectValue<?>> primitives = new HashMap<>();

    /**
     * Used in {@link GsonPlusBuilder} for creating classes
     */
    private static final HashMap<Class<?>, IDefaultObject<?>> objectAdapters = new HashMap<>();

    /**
     * the {@link ClassLoader} for loading all classes
     */
    private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    /**
     * should the default case be used if a value couldn't be found or cast
     */
    private static boolean useDefaultCase = false;

    /**
     * should a exception be thrown when a object couldn't be created
     */
    private static boolean throwObjectNullException = true;

    /**
     * default case or every boolean
     */
    private static boolean defaultBooleanCase = false;

    /**
     * default case for every number
     */
    private static Number defaultNumberCase = 0;

    /**
     * default case for every character
     */
    private static char defaultCharacter = 0;

    /**
     * default case for every string
     */
    private static String defaultString = "";

    /**
     * default case for every class
     */
    private static Class<?> defaultClass = null;

    /**
     * default case for every object
     */
    private static Object defaultObject = null;

    /**
     * default case for every enum
     */
    private static Enum<?> defaultEnum = null;

    /**
     * default case for every File
     */
    private static File defaultFile = null;
    /**
     * should the instances be added or the list be overwritten
     */
    private static boolean overwriteLists = false;

    /**
     * should use the lower class in the array
     */
    private static boolean lowerArrayClassPriority = false;


    /*
     * Both Class and primitives are used.
     */
    static {
        IObjectValue<Integer> integerIObjectValue = new IObjectValue<Integer>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsInt();
            }

            @Override
            public Integer getDefault() {
                return defaultNumberCase.intValue();
            }

            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(int.class, integerIObjectValue);
       addPrimitive(Integer.class, integerIObjectValue);

        IObjectValue<Long> longIObjectValue = new IObjectValue<Long>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsLong();
            }

            @Override
            public Long getDefault() {
                return defaultNumberCase.longValue();
            }

            @Override
            public String toString(Long object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(long.class, longIObjectValue);
       addPrimitive(Long.class, longIObjectValue);

        IObjectValue<Short> shortIObjectValue = new IObjectValue<Short>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsShort();
            }

            @Override
            public Short getDefault() {
                return defaultNumberCase.shortValue();
            }

            @Override
            public String toString(Short object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(short.class, shortIObjectValue);
       addPrimitive(Short.class, shortIObjectValue);

        IObjectValue<Byte> byteIObjectValue = new IObjectValue<Byte>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsByte();
            }

            @Override
            public Byte getDefault() {
                return defaultNumberCase.byteValue();
            }

            @Override
            public String toString(Byte object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(byte.class, byteIObjectValue);
       addPrimitive(Byte.class, byteIObjectValue);

        IObjectValue<Character> characterIObjectValue = new IObjectValue<Character>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsCharacter();
            }

            @Override
            public Character getDefault() {
                return defaultCharacter;
            }

            @Override
            public String toString(Character object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(char.class, characterIObjectValue);
       addPrimitive(Character.class, characterIObjectValue);

        IObjectValue<Float> floatIObjectValue = new IObjectValue<Float>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsFloat();
            }

            @Override
            public Float getDefault() {
                return defaultNumberCase.floatValue();
            }

            @Override
            public String toString(Float object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(float.class, floatIObjectValue);
       addPrimitive(Float.class, floatIObjectValue);

        IObjectValue<Double> doubleIObjectValue = new IObjectValue<Double>() {
            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsDouble();
            }

            @Override
            public Double getDefault() {
                return defaultNumberCase.doubleValue();
            }

            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(double.class, doubleIObjectValue);
       addPrimitive(Double.class, doubleIObjectValue);

        IObjectValue<Boolean> booleanIObjectValue = new IObjectValue<Boolean>() {

            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsBoolean();
            }

            @Override
            public Boolean getDefault() {
                return defaultBooleanCase;
            }

            @Override
            public String toString(Boolean object) {
                return String.valueOf(object);
            }
        };
       addPrimitive(boolean.class, booleanIObjectValue);
       addPrimitive(Boolean.class, booleanIObjectValue);

        IObjectValue<String> stringIObjectValue = new IObjectValue<String>() {

            @Override
            public Object toObject(JsonElement primitive) {
                return primitive.getAsJsonPrimitive().getAsString();
            }

            @Override
            public String getDefault() {
                return defaultString;
            }

            @Override
            public String toString(String object) {
                return object;
            }
        };
       addPrimitive(String.class, stringIObjectValue);

        IObjectValue<Class<?>> classIObjectValue = new IObjectValue<Class<?>>() {

            @Override
            public Object toObject(JsonElement primitive) {
                try {
                    ClassLoader classLoader = GsonPlusConfig.classLoader != null ? GsonPlusConfig.classLoader : Thread.currentThread().getContextClassLoader();
                    return classLoader.loadClass(primitive.getAsJsonPrimitive().getAsString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return defaultClass;
            }
            @Override
            public Class<?> getDefault() {
                return defaultClass;
            }

            @Override
            public String toString(Class<?> object) {
                return object.getName();
            }
        };
       addPrimitive(Class.class, classIObjectValue);


       IObjectValue<File> fileIObjectValue = new IObjectValue<File>() {
           @Override
           public Object toObject(JsonElement primitive) {
               return new File(primitive.getAsJsonPrimitive().getAsString());
           }

           @Override
           public File getDefault() {
               return defaultFile;
           }

           @Override
           public String toString(File object) {
               return object.getAbsolutePath();
           }
       };
       addPrimitive(File.class,fileIObjectValue);
    }



    public static ClassLoader getClassLoader() {
        return classLoader;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        GsonPlusConfig.classLoader = classLoader;
    }

    public static boolean shouldUseDefaultCase() {
        return useDefaultCase;
    }

    public static void setUseDefaultCase(boolean useDefaultCase) {
        GsonPlusConfig.useDefaultCase = useDefaultCase;
    }

    public static boolean shouldThrowObjectNullException() {
        return throwObjectNullException;
    }

    public static void setThrowObjectNullException(boolean throwObjectNullException) {
        GsonPlusConfig.throwObjectNullException = throwObjectNullException;
    }

    public static boolean getDefaultBooleanCase() {
        return defaultBooleanCase;
    }

    public static void setDefaultBooleanCase(boolean defaultBooleanCase) {
        GsonPlusConfig.defaultBooleanCase = defaultBooleanCase;
    }

    public static Number getDefaultNumberCase() {
        return defaultNumberCase;
    }

    public static void setDefaultNumberCase(Number defaultNumberCase) {
        GsonPlusConfig.defaultNumberCase = defaultNumberCase;
    }

    public static char getDefaultCharacter() {
        return defaultCharacter;
    }

    public static void setDefaultCharacter(char defaultCharacter) {
        GsonPlusConfig.defaultCharacter = defaultCharacter;
    }

    public static String getDefaultString() {
        return defaultString;
    }

    public static void setDefaultString(String defaultString) {
        GsonPlusConfig.defaultString = defaultString;
    }

    public static Class<?> getDefaultClass() {
        return defaultClass;
    }

    public static void setDefaultClass(Class<?> defaultClass) {
        GsonPlusConfig.defaultClass = defaultClass;
    }

    public static Object getDefaultObject() {
        return defaultObject;
    }

    public static void setDefaultObject(Object defaultObject) {
        GsonPlusConfig.defaultObject = defaultObject;
    }

    public static Enum<?> getDefaultEnum() {
        return defaultEnum;
    }

    public static void setDefaultEnum(Enum<?> defaultEnum) {
        GsonPlusConfig.defaultEnum = defaultEnum;
    }

    public static boolean shouldOverwriteLists() {
        return overwriteLists;
    }

    public static void setOverwriteLists(boolean overwriteLists) {
        GsonPlusConfig.overwriteLists = overwriteLists;
    }

    public static File getDefaultFile() {
        return defaultFile;
    }

    public static void setDefaultFile(File defaultFile) {
        GsonPlusConfig.defaultFile = defaultFile;
    }

    public static boolean shouldUseLowerArrayClassPriority() {
        return lowerArrayClassPriority;
    }

    public static void setLowerArrayClassPriority(boolean lowerArrayClassPriority) {
        GsonPlusConfig.lowerArrayClassPriority = lowerArrayClassPriority;
    }

    public static void addPrimitive(Class<?> classTarget,IObjectValue<?> objectValue){
        primitives.put(classTarget,objectValue);
    }

    public static HashMap<Class<?>, IObjectValue<?>> getAllPrimitives() {
        return primitives;
    }

    public static <A> void addAdapter(Class<? extends A> classTarget, IDefaultObject<A> objectValue) {
        objectAdapters.put(classTarget,objectValue);
    }

    public static HashMap<Class<?>, IDefaultObject<?>> getObjectAdapters() {
        return objectAdapters;
    }

    /**
     * the interface for abstract use for primitives
     */
    public interface IObjectValue<V> {
        Object toObject(JsonElement primitive);

        V getDefault();

        String toString(V object);
    }

    /**
     * the interface for abstract use for Adapters
     */
    public interface IDefaultObject<A> {
        A createObject();
    }

}
