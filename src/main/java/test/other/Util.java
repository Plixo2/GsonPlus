package test.other;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Util {


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
