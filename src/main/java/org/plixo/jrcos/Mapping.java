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

    /*
     * Both Class and primitives are used.
     */
    static {
        primitives.put(int.class, JsonPrimitive::getAsInt);
        primitives.put(Integer.class, JsonPrimitive::getAsInt);

        primitives.put(long.class, JsonPrimitive::getAsLong);
        primitives.put(Long.class, JsonPrimitive::getAsLong);

        primitives.put(short.class, JsonPrimitive::getAsShort);
        primitives.put(Short.class, JsonPrimitive::getAsShort);

        primitives.put(byte.class, JsonPrimitive::getAsByte);
        primitives.put(Byte.class, JsonPrimitive::getAsByte);

        primitives.put(char.class, JsonPrimitive::getAsCharacter);
        primitives.put(Character.class, JsonPrimitive::getAsCharacter);

        primitives.put(float.class, JsonPrimitive::getAsFloat);
        primitives.put(Float.class, JsonPrimitive::getAsFloat);

        primitives.put(double.class, JsonPrimitive::getAsDouble);
        primitives.put(Double.class, JsonPrimitive::getAsDouble);

        primitives.put(boolean.class, JsonPrimitive::getAsBoolean);
        primitives.put(Boolean.class, JsonPrimitive::getAsBoolean);

        primitives.put(String.class, JsonPrimitive::getAsString);

        /* create Adapters here, or elsewhere in your code */

        //objectAdapter.put(Vector3D.class, () -> new Vector3D(0,0,0));
    }

    /**
     * the interface for abstract use for primitives
     */
    public interface IObjectValue {
        Object toObject(JsonPrimitive primitive);
    }

    /**
     * the interface for abstract use for Adapters
     */
    public interface IObjectCreation {
        Object createObject();
    }

}
