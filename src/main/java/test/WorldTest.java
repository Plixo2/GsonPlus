package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.gsonplus.GsonPlusBuilder;
import org.plixo.gsonplus.GsonPlus;
import org.plixo.gsonplus.GsonPlusConfig;
import test.world.World;
import org.plixo.gsonplus.Util;

import java.io.File;

public class WorldTest {

    public static void main(String[] args) {
        System.out.println("GsonPlus v1.6 2D World Example");

        File original = new File("world/original.json");
        GsonPlusConfig.setClassLoader(WorldTest.class.getClassLoader());

        try {

            World filledWorld = new World(); //create a world
            filledWorld.fill(); //fill that world with green cubes

            GsonPlus gsonPlus = new GsonPlus(); //instance for saving
            JsonElement object = gsonPlus.toJson(filledWorld); //create a JsonElement from that world
            System.out.println("Original " + object); //debug print
            Util.saveJsonObj(original, object); //safe that to "world/original.json" with own method

            World emptyWorld = new World(); //create an empty world without cubes
            JsonObject jsonObject = Util.loadFromJson(original); //load from "world/original.json" into the JsonObject
            GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder(); //instance for building
            gsonPlusBuilder.create(emptyWorld, jsonObject); //load the cubes into the 2d array from the JsonObject
            JsonElement copyObject = gsonPlus.toJson(emptyWorld); //recreate the JsonElement from above
            System.out.println("Copy.... " + copyObject); //debug with the new values (should be the same)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
