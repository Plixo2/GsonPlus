package org.plixo.gsonplus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Feed data to objects saved previously.
 * The {@link GsonPlusBuilder} can create new objects via {@link GsonPlusConfig.IDefaultObject} in {@link GsonPlusConfig} or with empty constructors in the dependent class.
 * Also arrays, when not initialised previously, are getting created (size = 0).
 * Arrays with the wrong size will lose data!
 * The size of a list is resized to the saved data.
 *
 * @author Plixo
 * @see GsonPlus for saving
 */
public class GsonPlusBuilder {

    /**
     * Used to alter or create values from the reference using the saved data in the {@link JsonElement}.
     *
     * @param objectReference The reference where values should be altered (or created).
     * @param jsonObject      the {@link JsonElement} where the data for the reference is stored. Also used for recursion.
     * @return the reference again (used for the primitives in {@link GsonPlusConfig} without using the pointer equivalent, like {@link Integer})
     * @throws ReflectiveOperationException for error in instance creation
     */
    public Object create(Object objectReference, JsonElement jsonObject) throws ReflectiveOperationException, NullPointerException {

        if (objectReference == null) {
            if (GsonPlusConfig.shouldThrowObjectNullException()) {
                throw new NullPointerException("The Object is null. Use an adapter or initialize your fields");
            }
            return null;
        }

        Class<?> objectClass = objectReference.getClass();
        if (jsonObject == null) {

            if (GsonPlusConfig.getAllPrimitives().containsKey(objectClass)) {
                return GsonPlusConfig.getAllPrimitives().get(objectClass).getDefault();
            } else {
                Object instance = createInstance(objectClass);
                if (instance != null) {
                    return instance;
                }
                return GsonPlusConfig.getDefaultObject();
            }

        }
        if (objectClass.isArray()) {
            JsonArray jsonArray = jsonObject.getAsJsonArray();
            Object[] objectArray = (Object[]) objectReference;
            int safeIterationSize = Math.min(objectArray.length, jsonArray.size());
            for (int i = 0; i < safeIterationSize; i++) {
                JsonObject toJsonObject = jsonArray.get(i).getAsJsonObject();
                Map.Entry<String, JsonElement> entry = toJsonObject.entrySet().iterator().next();
                String className = entry.getKey();

                if (objectArray[i] == null) {
                    if (GsonPlusConfig.shouldUseLowerArrayClassPriority()) {
                        objectArray[i] = createInstance(objectArray.getClass().getComponentType());
                    } else {
                        objectArray[i] = createInstance(className);
                    }

                }
                objectArray[i] = create(objectArray[i], entry.getValue());
            }
            return objectReference;

        } else if (objectReference instanceof List) {

            JsonArray jsonArray = jsonObject.getAsJsonArray();
            List<Object> list = (List<Object>) objectReference;
            if (GsonPlusConfig.shouldOverwriteLists()) {
                list.clear();
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject toJsonObject = jsonArray.get(i).getAsJsonObject();
                Map.Entry<String, JsonElement> entry = toJsonObject.entrySet().iterator().next();
                Class<?> clazz = Class.forName(entry.getKey());
                Object newInstance = createInstance(clazz);
                list.add(create(newInstance, entry.getValue()));
            }
            return objectReference;

        } else if (objectClass.isEnum()) {

            String type = jsonObject.getAsJsonPrimitive().getAsString();
            Enum<?>[] enumConstants = ((Enum<?>) objectReference).getDeclaringClass().getEnumConstants();
            for (Enum<?> enumConstant : enumConstants) {
                if (enumConstant.name().equalsIgnoreCase(type)) {
                    return enumConstant;
                }
            }
            return GsonPlusConfig.getDefaultEnum();

        } else if (GsonPlusConfig.getAllPrimitives().containsKey(objectClass)) {

            GsonPlusConfig.IObjectValue<?> iObjectValue = GsonPlusConfig.getAllPrimitives().get(objectClass);
            return iObjectValue.toObject(jsonObject);

        } else {

            for (Field field : GsonPlus.getFields(objectClass)) {

                field.setAccessible(true);
                JsonElement subObj = jsonObject.getAsJsonObject().get(field.getName());
                Object objectToUpdate = field.get(objectReference);
                if (objectToUpdate == null) {
                    if (field.isAnnotationPresent(Optional.class)) {
                        continue;
                    } else {
                        Class<?> type = field.getType();
                        objectToUpdate = createInstance(type);
                        if (objectToUpdate == null && GsonPlusConfig.shouldThrowObjectNullException()) {
                            throw new NullPointerException(type.getName() + " couldn't be created. " +
                                    "Create an adapter in the config class " +
                                    "or create an empty constructor in " + type.getName());

                        }
                    }
                }
                Object objectFromJson = create(objectToUpdate, subObj);
                if (objectFromJson == null) {
                    if (!GsonPlusConfig.shouldUseDefaultCase()) {
                        continue;
                    }

                    if (GsonPlusConfig.getAllPrimitives().containsKey(field.getType())) {
                        objectFromJson = GsonPlusConfig.getAllPrimitives().get(field.getType()).getDefault();
                    } else {
                        objectFromJson = GsonPlusConfig.getDefaultObject();
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
    private Object createInstance(Class<?> instanceClass) throws ReflectiveOperationException {
        return createInstance(instanceClass.getName());
    }

    /**
     * @param typeName the class name of the dependent object
     * @return the created instance or null if a instance couldn't be created.
     * @throws ReflectiveOperationException for error in instance creation
     */
    private Object createInstance(String typeName) throws ReflectiveOperationException {

        ClassLoader classLoader = GsonPlusConfig.getClassLoader() != null ? GsonPlusConfig.getClassLoader() : Thread.currentThread().getContextClassLoader();
        Class<?> type = classLoader.loadClass(typeName);

        if (GsonPlusConfig.getObjectAdapters().containsKey(type)) {
            return GsonPlusConfig.getObjectAdapters().get(type).createObject();
        }

        if (type.isArray()) {
            return Array.newInstance(type.getComponentType(), 0);
        }

        for (Constructor<?> constructor : type.getConstructors()) {
            constructor.setAccessible(true);
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
        }

        return null;
    }
}
