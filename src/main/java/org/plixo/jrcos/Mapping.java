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
    public static HashMap<Class<?>, IObjectValue> primitives = new HashMap<>();

    /**
     * Used in {@link Initializer} for creating classes
     */
    public static HashMap<Class<?>, IObjectCreation> objectAdapters = new HashMap<>();

    /**
     * should the instances be added or the list be overwritten
     */
    public static boolean overwriteLists = false;

    /*
     * Both Class and primitives are used.
     */
    static {
        IObjectValue<Integer> integerIObjectValue = new IObjectValue<Integer>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsInt();
            }

            @Override
            public Integer getDefault() {
                return 0;
            }

            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }
        };
        primitives.put(int.class, integerIObjectValue);
        primitives.put(Integer.class, integerIObjectValue);

        IObjectValue<Long> longIObjectValue = new IObjectValue<Long>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsLong();
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public String toString(Long object) {
                return String.valueOf(object);
            }
        };
        primitives.put(long.class, longIObjectValue);
        primitives.put(Long.class, longIObjectValue);

        IObjectValue<Short> shortIObjectValue = new IObjectValue<Short>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsShort();
            }

            @Override
            public Short getDefault() {
                return 0;
            }

            @Override
            public String toString(Short object) {
                return String.valueOf(object);
            }
        };
        primitives.put(short.class, shortIObjectValue);
        primitives.put(Short.class, shortIObjectValue);

        IObjectValue<Byte> byteIObjectValue = new IObjectValue<Byte>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsByte();
            }

            @Override
            public Byte getDefault() {
                return 0;
            }

            @Override
            public String toString(Byte object) {
                return String.valueOf(object);
            }
        };
        primitives.put(byte.class, byteIObjectValue);
        primitives.put(Byte.class, byteIObjectValue);

        IObjectValue<Character> characterIObjectValue = new IObjectValue<Character>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsCharacter();
            }

            @Override
            public Character getDefault() {
                return 0;
            }

            @Override
            public String toString(Character object) {
                return String.valueOf(object);
            }
        };
        primitives.put(char.class, characterIObjectValue);
        primitives.put(Character.class, characterIObjectValue);

        IObjectValue<Float> floatIObjectValue = new IObjectValue<Float>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsFloat();
            }

            @Override
            public Float getDefault() {
                return 0f;
            }

            @Override
            public String toString(Float object) {
                return String.valueOf(object);
            }
        };
        primitives.put(float.class, floatIObjectValue);
        primitives.put(Float.class, floatIObjectValue);

        IObjectValue<Double> doubleIObjectValue = new IObjectValue<Double>() {
            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsDouble();
            }

            @Override
            public Double getDefault() {
                return 0d;
            }

            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }
        };
        primitives.put(double.class, doubleIObjectValue);
        primitives.put(Double.class, doubleIObjectValue);

        IObjectValue<Boolean> booleanIObjectValue = new IObjectValue<Boolean>() {

            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsBoolean();
            }

            @Override
            public Boolean getDefault() {
                return false;
            }

            @Override
            public String toString(Boolean object) {
                return String.valueOf(object);
            }
        };
        primitives.put(boolean.class, booleanIObjectValue);
        primitives.put(Boolean.class, booleanIObjectValue);

        IObjectValue<String> stringIObjectValue = new IObjectValue<String>() {

            @Override
            public Object toObject(JsonPrimitive primitive) {
                return primitive.getAsString();
            }

            @Override
            public String getDefault() {
                return "";
            }

            @Override
            public String toString(String object) {
                return object;
            }
        };
        primitives.put(String.class, stringIObjectValue);

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
