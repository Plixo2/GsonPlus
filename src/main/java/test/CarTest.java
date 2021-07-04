package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Serializer;
import test.car.Car;
import test.car.Vector2D;
import test.other.Util;

import java.io.File;

public class CarTest {

    public static void main(String[] args) {
        System.out.println("Serializer v1.1 Car Example");

        File original = new File("car/original.json");

        Car germanCars = new Car(10, "German", 4.5f, new Vector2D(1337, 420), false, "Audi","BMW", "Mercedes");
        Car otherCars = new Car(5, "Other", 2.25f, new Vector2D(6.25, 6), true, "Opel", "Ford", "Ferrari");

        try {

            JsonElement object = Serializer.getJsonFromObject(germanCars); //create a JsonElement from the german car
            Util.saveJsonObj(original, object); //safe that to "car/original.json" with own method
            System.out.println("Saved... " + germanCars); //debug print

            JsonObject jsonObject = Util.loadFromJson(original); //load from "car/original.json" into the JsonObject
            Initializer.getObjectFromJson(otherCars, jsonObject); //paste the values into the other car
            System.out.println("Loaded.. " + otherCars); //debug with the new values (should be the same)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
