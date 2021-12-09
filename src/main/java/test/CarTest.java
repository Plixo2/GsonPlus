package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.gsonplus.GsonPlusBuilder;
import org.plixo.gsonplus.GsonPlusConfig;
import org.plixo.gsonplus.GsonPlus;
import test.car.Car;
import test.car.Vector2D;
import org.plixo.gsonplus.Util;

import java.io.File;

public class CarTest {

    public static void main(String[] args) {
        System.out.println("GsonPlus v1.7.1 2D Car Example");

        File original = new File("car/original.json");

        Car germanCars = new Car(10, "German", 2.3f, new Vector2D(1337, 420), false, "Audi","BMW", "Mercedes");
        Car otherCars = new Car(5, "Other", 2.25f, new Vector2D(6.25, 6), true, "Opel", "Ford", "Ferrari");

        try {
            GsonPlusConfig.setOverwriteLists(true); //the second list should be cleared and overwritten
            GsonPlusConfig.setClassLoader(CarTest.class.getClassLoader());

            GsonPlus gsonPlus = new GsonPlus(); //instance for saving
            JsonElement object = gsonPlus.toJson(germanCars); //create a JsonElement from the german car
            Util.saveJsonObj(original, object); //safe that to "car/original.json" with own method
            System.out.println("Saved... " + germanCars); //debug print

            GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder(); //instance for building
            JsonObject jsonObject = Util.loadFromJson(original); //load from "car/original.json" into the JsonObject
            gsonPlusBuilder.create(otherCars, jsonObject); //paste the values into the other car
            System.out.println("Loaded.. " + otherCars); //debug with the new values (should be the same, except the parent Vector is no null)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
