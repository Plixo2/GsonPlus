package org.plixo.gsonplus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Saves all data from a given object, including all unknown objects.
 * Arrays and list are also getting saved.
 * Primitives (int, short, byte, long, char, float, double, boolean, String) are directly saved via {@link GsonPlusConfig.IObjectValue} in {@link GsonPlusConfig}
 *
 * @author Plixo
 * @see GsonPlusBuilder for loading
 */
public class GsonPlus {

    /**
     * Used to save the data from an object to a {@link JsonObject}, which can later be saved.
     *
     * @param object the object that should be saved. Also used for recursion.
     * @return a {@link JsonElement} for saving or recursive use in an {@link JsonObject}
     * @throws IllegalAccessException for illegal field access
     */
    public JsonElement toJson(Object object) throws IllegalAccessException,NullPointerException {

        if (object == null) {
            return null;
        }

        Class<?> clazz = object.getClass();

        if (clazz.isArray() || object instanceof List) {

            Object[] objectArray = (object instanceof List) ? ((List<?>) object).toArray() : (Object[]) object;
            JsonArray jsonArray = new JsonArray();
            for (Object arrayObject : objectArray) {
                JsonElement other = toJson(arrayObject);
                if (other != null) {
                    JsonObject object1 = new JsonObject();
                    object1.add(arrayObject.getClass().getName(), other);
                    jsonArray.add(object1);
                }
            }
            return jsonArray;

        } else if (clazz.isEnum()) {

            Enum<?> anEnum = ((Enum<?>) object);
            return new JsonPrimitive(anEnum.name());

        } else if (GsonPlusConfig.getAllPrimitives().containsKey(clazz)) {

            GsonPlusConfig.IObjectValue<Object> iObjectValue = (GsonPlusConfig.IObjectValue<Object>) GsonPlusConfig.getAllPrimitives().get(clazz);
            String value = iObjectValue.toString(object);
            return new JsonPrimitive(value);

        } else {

            JsonObject jsonObject = new JsonObject();
            for (Field field : clazz.getFields()) {
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Object object1 = field.get(object);
                JsonElement other = toJson(object1);
                if (other != null)
                    jsonObject.add(field.getName(), other);
            }
            return jsonObject;

        }
    }

    /**
     * could be used...
     *
     * @param clazz the class, where the recursive search starts
     * @return the full list of all fields
     */
    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;
        while (current.getSuperclass() != null) {
            if (current == Object.class) {
                break;
            }
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

}
