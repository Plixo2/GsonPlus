package org.plixo.gsonplus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Util class for saving and loading {@link JsonElement}.
 * another class can be used!
 */
public class Util {

    /**
     * load the file context in a {@link JsonObject}
     * @param file the file of the saved object
     * @return the {@link JsonObject} containing the data
     */
    public static JsonObject loadFromJson(File file) {
        try {
            if (!file.exists()) {
                makeFile(file);
                return null;
            }
            FileReader reader = new FileReader(file);
            JsonObject asJsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            return asJsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * saves a file to the disk
     * @param file where the {@link JsonElement} should be saved
     * @param json the {@link JsonElement} containing all the data
     */
    public static void saveJsonObj(File file, JsonElement json) {
        try {
            if (!file.exists()) {
                makeFile(file);
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * will create a file if it doesn't exist.
     * @param file the location of the file
     */
    public static void makeFile(File file) {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
