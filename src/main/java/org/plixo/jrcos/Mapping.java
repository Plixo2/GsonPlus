package org.plixo.jrcos;

import com.google.gson.JsonPrimitive;

import java.util.HashMap;

/**
 * Used for mapping for the primitives via {@link IObjectValue}
 * and {@link IObjectCreation} for default instances, when empty constructors in the Class doesn't fit.
 *
 * @author Plixo
 */
public class Mapping {

    /**
     * Used in {@link Initializer} and {@link Serializer}
     */
    public static HashMap<Class<?>, IObjectValue<?>> primitives = new HashMap<>();

    /**
     * Used in {@link Initializer} for creating classes
     */
    public static HashMap<Class<?>, IObjectCreation> objectAdapters = new HashMap<>();

    /**
     * should the default case be used if a value couldn't be found or cast
     */
    public static boolean useDefaultCase = false;

    /**
     * should a exception be thrown when a object couldn't be created
     */
    public static boolean throwObjectNullException = false;

    /**
     * default case or every boolean
     */
    public static boolean defaultBooleanCase = false;

    /**
     * default case for every number
     */
    public static Number defaultNumberCase = 0;

    /**
     * default case for every character
     */
    public static char defaultCharacter = 0;

    /**
     * default case for every string
     */
    public static String defaultString = "";

    /**
     * default case for every class
     */
    public static Class<?> defaultClass = null;

    /**
     * default case for every object
     */
    public static Object defaultObject = null;

    /**
     * should the instances be added or the list be overwritten
     */
    public static boolean overwriteLists = false;

    /**
     * should use an recursive function to get all fields in a class, including all superclasses
     */
    public static boolean recursiveFields = false;


    /*
     * Both Class and primitives are used.
     */
    static {
        IObjectValue<Integer> integerIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsInt();
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
        primitives.put(int.class, integerIObjectValue);
        primitives.put(Integer.class, integerIObjectValue);

        IObjectValue<Long> longIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsLong();
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
        primitives.put(long.class, longIObjectValue);
        primitives.put(Long.class, longIObjectValue);

        IObjectValue<Short> shortIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsShort();
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
        primitives.put(short.class, shortIObjectValue);
        primitives.put(Short.class, shortIObjectValue);

        IObjectValue<Byte> byteIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsByte();
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
        primitives.put(byte.class, byteIObjectValue);
        primitives.put(Byte.class, byteIObjectValue);

        IObjectValue<Character> characterIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsCharacter();
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
        primitives.put(char.class, characterIObjectValue);
        primitives.put(Character.class, characterIObjectValue);

        IObjectValue<Float> floatIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsFloat();
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
        primitives.put(float.class, floatIObjectValue);
        primitives.put(Float.class, floatIObjectValue);

        IObjectValue<Double> doubleIObjectValue = new IObjectValue<>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsDouble();
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
        primitives.put(double.class, doubleIObjectValue);
        primitives.put(Double.class, doubleIObjectValue);

        IObjectValue<Boolean> booleanIObjectValue = new IObjectValue<>() {

            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsBoolean();
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
        primitives.put(boolean.class, booleanIObjectValue);
        primitives.put(Boolean.class, booleanIObjectValue);

        IObjectValue<String> stringIObjectValue = new IObjectValue<>() {

            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsString();
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
        primitives.put(String.class, stringIObjectValue);

        IObjectValue<Class<?>> classIObjectValue = new IObjectValue<>() {

            @Override
            public Object toObject(JsonPrimitive primitive) {
                try {
                    return Class.forName(primitive.getAsString());
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
        primitives.put(Class.class, classIObjectValue);

        /* create Adapters here, or elsewhere in your code */
        //objectAdapter.put(Vector3D.class, () -> new Vector3D(0,0,0));
    }

    /**
     * the interface for abstract use for primitives
     */
    public interface IObjectValue<V> {
        Object toObject(JsonPrimitive primitive);

        V getDefault();

        String toString(V object);
    }

    /**
     * the interface for abstract use for Adapters
     */
    public interface IObjectCreation {
        Object createObject();
    }

}
