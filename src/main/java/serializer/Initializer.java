package serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.*;
import java.util.List;

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
     * Used to alter or create values from the object using the saved data in the {@link JsonElement}.
     *
     * @param object     The object where values should be altered (or created).
     * @param jsonObject the {@link JsonElement} where the data for the object is stored. Also used for recursion.
     * @return the object again (used for the primitives in {@link Mapping} without using the pointer equivalent, like {@link Integer})
     * @throws ReflectiveOperationException
     */
    public static Object getObjectFromJson(Object object, JsonElement jsonObject) throws ReflectiveOperationException {

        if(object == null) {
             throw new NullPointerException("The Object is null. Use an adapter or initialize your fields");
        }
        if (jsonObject == null) {
            return null;
        }
        Class<?> objectClass = object.getClass();
        if (objectClass.isArray()) {
            JsonArray jsonArray = jsonObject.getAsJsonArray();
            Object[] objectArray = (Object[]) object;
            int safeIterationSize = Math.min(objectArray.length, jsonArray.size());

            for (int i = 0; i < safeIterationSize; i++) {
                JsonElement element = jsonArray.get(i);
                objectArray[i] = getObjectFromJson(objectArray[i], element);
            }

            return object;

        } else if (object instanceof List) {

            JsonArray jsonArray = jsonObject.getAsJsonArray();
            List list = (List) object;
            int safeIterationSize = Math.min(list.size(), jsonArray.size());

            for (int i = 0; i < safeIterationSize; i++) {
                JsonElement element = jsonArray.get(i);
                list.set(i, getObjectFromJson(list.get(i), element));
            }

            object = list.subList(0, safeIterationSize);
            return object;

        } else if (Mapping.primitives.containsKey(objectClass)) {

            Mapping.IObjectValue iObjectValue = Mapping.primitives.get(objectClass);
            return iObjectValue.toObject(jsonObject.getAsJsonPrimitive());

        } else {

            for (Field field : objectClass.getFields()) {

                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);
                JsonElement subObj = jsonObject.getAsJsonObject().get(field.getName());
                Object objectToUpdate = field.get(object);
                if (objectToUpdate == null) {

                    Class<?> type = (Class<?>) field.getGenericType();
                    objectToUpdate = createInstance(type);
                    if (objectToUpdate == null) {

                        throw new NullPointerException(type.getName() + " couldn't be created. " +
                                "Create an adapter in the mapping class " +
                                "or create an empty constructor in " + type.getName());

                    }

                }
                Object objectFromJson = getObjectFromJson(objectToUpdate, subObj);
                field.set(object, objectFromJson);
            }

        }
        return object;
    }

    /**
     * @param instanceClass the class of the dependent object
     * @return the created instance or null if a instance couldn't be created.
     * @throws ReflectiveOperationException
     */
    static Object createInstance(Class<?> instanceClass) throws ReflectiveOperationException {

        if (Mapping.objectAdapters.containsKey(instanceClass)) {
            return Mapping.objectAdapters.get(instanceClass).createObject();
        }

        if (instanceClass.isArray()) {
            return Array.newInstance(instanceClass.getComponentType(), 0);
        }

        for (Constructor<?> constructor : instanceClass.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
        }

        return null;
    }
}
