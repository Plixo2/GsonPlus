package test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.jrcos.Util;

import java.io.File;
import java.util.Map;

public class Different {
    public static void main(String[] args) {
        File f = new File("colors.json");
        File f2 = new File("colorsOut.json");
        JsonObject object = Util.loadFromJson(f);

        JsonArray array = new JsonArray();
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : object.entrySet()) {
          array.add(  stringJsonElementEntry.getValue());
        }


        Util.saveJsonObj(f2,array);
    }
}
