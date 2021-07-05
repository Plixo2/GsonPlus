package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Serializer;
import test.arrays.World;
import org.plixo.jrcos.Util;

import java.io.File;

public class WorldTest {

    public static void main(String[] args) {
        System.out.println("Serializer v1.2 2D World Array Example");

        File original = new File("world/original.json");
        try {
            
            World filledWorld = new World(); //create a world
            filledWorld.fill(); //fill that world with green cubes
            JsonElement object = Serializer.getJson(filledWorld); //create a JsonElement from that world
            System.out.println("Original " + object); //debug print
            Util.saveJsonObj(original, object); //safe that to "world/original.json" with own method

            World emptyWorld = new World(); //create an empty world without cubes
            JsonObject jsonObject = Util.loadFromJson(original); //load from "world/original.json" into the JsonObject
            Initializer.getObject(emptyWorld, jsonObject); //load the cubes into the 2d array from the JsonObject
            JsonElement copyObject = Serializer.getJson(emptyWorld); //recreate the JsonElement from above
            System.out.println("Copy.... " + copyObject); //debug with the new values (should be the same)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
