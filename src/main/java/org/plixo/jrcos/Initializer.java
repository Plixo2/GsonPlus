package org.plixo.jrcos;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * Feed data to objects saved previously.
 * The {@link Initializer} can create new objects via {@link Mapping.IObjectCreation} in {@link Mapping} or with empty constructors in the dependent class.
 * Also arrays, when not initialised previously, are getting created (size = 0).
 * Arrays with the wrong size will lose data!
 * The size of a list is resized to the saved data.
 *
 * @author Plixo
 * @see Serializer for saving
 */
public class Initializer {
    /**
     * Used to alter or create values from the reference using the saved data in the {@link JsonElement}.
     *
     * @param objectReference The reference where values should be altered (or created).
     * @param jsonObject the {@link JsonElement} where the data for the reference is stored. Also used for recursion.
     * @return the reference again (used for the primitives in {@link Mapping} without using the pointer equivalent, like {@link Integer})
     * @throws ReflectiveOperationException for error in instance creation
     */
    public static Object getObject(Object objectReference, JsonElement jsonObject) throws ReflectiveOperationException {

        if (objectReference == null) {
            if(Mapping.throwObjectNullException) {
                throw new NullPointerException("The Object is null. Use an adapter or initialize your fields");
            }
            return null;
        }
        Class<?> objectClass = objectReference.getClass();
        if (jsonObject == null) {
            if (Mapping.primitives.containsKey(objectClass)) {
                return Mapping.primitives.get(objectClass).getDefault();
            } else {
                Object instance = createInstance(objectClass);
                if(instance != null) {
                    return instance;
                }
                return Mapping.defaultObject;
            }
        }

        if (objectClass.isArray()) {
            JsonArray jsonArray = jsonObject.getAsJsonArray();
            Object[] objectArray = (Object[]) objectReference;
            int safeIterationSize = Math.min(objectArray.length, jsonArray.size());
//length of jsonArray and creating of instances as option
            for (int i = 0; i < safeIterationSize; i++) {
                JsonObject toJsonObject = jsonArray.get(i).getAsJsonObject();
                Map.Entry<String, JsonElement> entry = toJsonObject.entrySet().iterator().next();
                String className = entry.getKey();
                if (objectArray[i] == null) {
                    objectArray[i] = createInstance(objectArray.getClass().getComponentType());
                }
                objectArray[i] = getObject(objectArray[i], entry.getValue());
            }

            return objectReference;

        } else if (objectReference instanceof List) {

            JsonArray jsonArray = jsonObject.getAsJsonArray();
            List<Object> list = (List<Object>) objectReference;
            if (Mapping.overwriteLists) {
                list.clear();
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject toJsonObject = jsonArray.get(i).getAsJsonObject();
                Map.Entry<String, JsonElement> entry = toJsonObject.entrySet().iterator().next();
                Class<?> clazz = Class.forName(entry.getKey());
                Object newInstance = createInstance(clazz);
                list.add(getObject(newInstance, entry.getValue()));
            }
            return objectReference;

        } else if (Mapping.primitives.containsKey(objectClass)) {

            Mapping.IObjectValue<?> iObjectValue = Mapping.primitives.get(objectClass);
            return iObjectValue.toObject(jsonObject.getAsJsonPrimitive());

        } else {

            for (Field field : objectClass.getFields()) {

                if (Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                JsonElement subObj = jsonObject.getAsJsonObject().get(field.getName());
                Object objectToUpdate = field.get(objectReference);
                if (objectToUpdate == null) {
                    Class<?> type = field.getType();
                    objectToUpdate = createInstance(type);
                    if (objectToUpdate == null && Mapping.throwObjectNullException) {
                        throw new NullPointerException(type.getName() + " couldn't be created. " +
                                "Create an adapter in the mapping class " +
                                "or create an empty constructor in " + type.getName());

                    }
                }
                Object objectFromJson = getObject(objectToUpdate, subObj);
                if (objectFromJson == null) {
                    if (!Mapping.useDefaultCase) {
                        continue;
                    }
                    if (Mapping.primitives.containsKey(field.getType())) {
                        objectFromJson = Mapping.primitives.get(field.getType()).getDefault();
                    } else {
                        objectFromJson = Mapping.defaultObject;
                    }
                }

                field.set(objectReference, objectFromJson);
            }

        }
        return objectReference;
    }

    /**
     * @param instanceClass the class of the dependent object
     * @return the created instance or null if a instance couldn't be created.
     * @throws ReflectiveOperationException for error in instance creation
     */
    static Object createInstance(Class<?> instanceClass) throws ReflectiveOperationException {

        ClassLoader classLoader = Mapping.classLoader != null ? Mapping.classLoader : Thread.currentThread().getContextClassLoader();
        Class<?> type = classLoader.loadClass(instanceClass.getName());

        if (Mapping.objectAdapters.containsKey(type)) {
            return Mapping.objectAdapters.get(type).createObject();
        }

        if (type.isArray()) {
            return Array.newInstance(type.getComponentType(), 0);
        }

        for (Constructor<?> constructor : type.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
        }

        return null;
    }
}
